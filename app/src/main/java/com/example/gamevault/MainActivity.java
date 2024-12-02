package com.example.gamevault;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.gamevault.database.GameVaultRepository;
import com.example.gamevault.database.entities.GameVault;
import com.example.gamevault.database.entities.User;
import com.example.gamevault.viewHolders.GameVaultViewModel;
import com.example.gamevault.R;
import com.example.gamevault.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "gamevault.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "gamevault.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "gamevault.SAVED_INSTANCE_STATE_USERID_KEY";



    private static final int LOGGED_OUT = -1;
    private ActivityMainBinding binding;
    private GameVaultRepository repository;
    private GameVaultViewModel gameVaultViewModel;

    public static final String TAG = "DAC_gamevault";

    String mExercise = "";
    double mWeight = 0.0;
    int mReps = 0;

    private int loggedInUserId = -1;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        gameVaultViewModel = new ViewModelProvider(this).get(GameVaultViewModel.class);


//        RecyclerView recyclerView = binding.logDisplayRecyclerView;
//        final gamevaultAdapter adapter = new gamevaultAdapter(new gamevaultAdapter.gamevaultDiff());
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = GameVaultRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

//        gamevaultViewModel.getAllLogsById(loggedInUserId).observe(this, gamevaults -> {
//            adapter.submitList(gamevaults);
//        });

        //User is not logged in at this point, go to login screen
        if (loggedInUserId == -1) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        updateSharedPreference();


        binding.adminButton.setOnClickListener(v -> {
            Toast.makeText(this, "This is only available to admins!", Toast.LENGTH_SHORT).show();
        });

    }


    private void loginUser(Bundle savedInstanceState) {
        // Check shared preference for logged in user
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);

        loggedInUserId = sharedPreferences.getInt(getString(R.string.preference_userId_key), LOGGED_OUT);

        if (loggedInUserId == LOGGED_OUT && savedInstanceState != null && savedInstanceState.containsKey(SAVED_INSTANCE_STATE_USERID_KEY)) {
            loggedInUserId = savedInstanceState.getInt(SAVED_INSTANCE_STATE_USERID_KEY, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            loggedInUserId = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID, LOGGED_OUT);
        }
        if (loggedInUserId == LOGGED_OUT) {
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
                updateAdminUI(user.isAdmin());
            }
        });
    }

    private void updateAdminUI(boolean isAdmin) {
        if (isAdmin) {
            binding.adminButton.setVisibility(View.VISIBLE);
        } else {
            binding.adminButton.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USERID_KEY, loggedInUserId);
        updateSharedPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if(user == null){
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        alertBuilder.setMessage("Logout?");

        alertBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logout();
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });

        alertBuilder.create().show();

    }

    private void logout() {

        loggedInUserId = LOGGED_OUT;
        updateSharedPreference();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    private void updateSharedPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor= sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
    }

    static Intent mainActivityIntentFactory(Context context, int userId){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID, userId);
        return intent;
    }

    private void insertgamevaultRecord(){
        if(mExercise.isEmpty()){
            return;
        }
        GameVault log = new GameVault(mExercise, mWeight, mReps, loggedInUserId);
        repository.insertgamevault(log);
    }

    @Deprecated
    private void updateDisplay() {
        ArrayList<GameVault> allLogs = repository.getAllLogsByUserId(loggedInUserId);
        if (allLogs.isEmpty()) {
   //         binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (GameVault log : allLogs) {
            sb.append(log);
        }
    //    binding.logDisplayTextView.setText(sb.toString());
    }


//    private void getInformationFromDisplay(){
//        mExercise = binding.exerciseInputEditText.getText().toString();
//        try {
//            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
//        } catch (NumberFormatException e){
//            Log.d(TAG, "Error reading value from Weight edit text.");
//        }
//
//        try {
//            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());
//        } catch (NumberFormatException e){
//            Log.d(TAG, "Error reading value from reps edit text.");
//        }
//    }
}