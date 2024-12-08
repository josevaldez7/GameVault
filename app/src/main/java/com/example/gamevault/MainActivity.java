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
import android.widget.TextView;
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
import com.example.gamevault.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String MAIN_ACTIVITY_USER_ID = "gamevault.MAIN_ACTIVITY_USER_ID";
    static final String SHARED_PREFERENCE_USERID_KEY = "gamevault.SHARED_PREFERENCE_USERID_KEY";
    static final String SAVED_INSTANCE_STATE_USERID_KEY = "gamevault.SAVED_INSTANCE_STATE_USERID_KEY";
    private static final String API_KEY = "ad341f2d8169dfd8ff8a2e335b11bd12b199e92c";
    private static final String API = "https://www.giantbomb.com/api/games/?api_key=ad341f2d8169dfd8ff8a2e335b11bd12b199e92c";



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

        binding.manageUsersButton.setOnClickListener(v -> showUserManagementDialog());
        binding.promoteUsersToAdminButton.setOnClickListener(v -> showPromoteToAdminDialog());

        binding.changeUserInfoButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ChangeInfoActivity.class);
            startActivity(intent);
        });

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


        binding.searchButton.setOnClickListener(v -> {
            Toast.makeText(this, "Search button worked", Toast.LENGTH_SHORT).show();
            Intent intent = SearchActivity.searchIntentFactory(MainActivity.this);
            startActivity(intent);
        });
        binding.exploreButton.setOnClickListener(v -> {
            Toast.makeText(this, "Explore button worked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ExploreActivity.class);
            startActivity(intent);
        });
        binding.browseButton.setOnClickListener(v -> {
            Toast.makeText(this, "browse button worked", Toast.LENGTH_SHORT).show();
            Intent intent = BrowseActivity.browseIntentFactory(MainActivity.this);
            startActivity(intent);
        });

    }

    private void showUserManagementDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        LiveData<List<User>> usersLiveData = repository.getAllUsers();
        usersLiveData.observe(this, users -> {
            if (users == null || users.isEmpty()) {
                Toast.makeText(this, "No users found!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> nonAdminUsers = new ArrayList<>();
            for (User user : users) {
                if (!user.isAdmin()) {
                    nonAdminUsers.add(user);
                }
            }
            if (nonAdminUsers.isEmpty()) {
                Toast.makeText(this, "No non-admin users found!", Toast.LENGTH_SHORT).show();
                return;
            }
            String[] userNames = new String[nonAdminUsers.size()];
            for (int i = 0; i < nonAdminUsers.size(); i++) {
                userNames[i] = nonAdminUsers.get(i).getUsername();
            }
            alertBuilder.setTitle("Manage Users");
            alertBuilder.setItems(userNames, (dialogInterface, which) -> {
                User selectedUser = nonAdminUsers.get(which);
                confirmAndRemoveUser(selectedUser);
            });
            alertBuilder.setNegativeButton("Cancel", (dialogInterface, which) -> alertDialog.dismiss());
            alertBuilder.create().show();
        });
    }

    private void confirmAndRemoveUser(User user) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setTitle("Remove User");
        alertBuilder.setMessage("Are you sure you want to remove " + user.getUsername() + "?");
        alertBuilder.setPositiveButton("Yes", (dialogInterface, which) -> {
            repository.deleteUser(user);
            Toast.makeText(this, "User removed successfully!", Toast.LENGTH_SHORT).show();
        });
        alertBuilder.setNegativeButton("No", (dialogInterface, which) -> alertDialog.dismiss());
        alertBuilder.create().show();
    }

    private void showPromoteToAdminDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();

        LiveData<List<User>> usersLiveData = repository.getAllUsers();
        usersLiveData.observe(this, users -> {
            if (users == null || users.isEmpty()) {
                Toast.makeText(this, "No users found!", Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> nonAdminUsers = new ArrayList<>();
            for (User user : users) {
                if (!user.isAdmin()) {
                    nonAdminUsers.add(user);
                }
            }

            if (nonAdminUsers.isEmpty()) {
                Toast.makeText(this, "No non-admin users found!", Toast.LENGTH_SHORT).show();
                return;
            }

            String[] userNames = new String[nonAdminUsers.size()];
            for (int i = 0; i < nonAdminUsers.size(); i++) {
                userNames[i] = nonAdminUsers.get(i).getUsername();
            }

            alertBuilder.setTitle("Promote Users to Admin");
            alertBuilder.setItems(userNames, (dialogInterface, which) -> {
                User selectedUser = nonAdminUsers.get(which);
                promoteToAdmin(selectedUser);
            });

            alertBuilder.setNegativeButton("Cancel", (dialogInterface, which) -> alertDialog.dismiss());
            alertBuilder.create().show();
        });
    }

    private void promoteToAdmin(User user) {
        if (user.isAdmin()) {
            Toast.makeText(this, "User is already an admin!", Toast.LENGTH_SHORT).show();
            return;
        }
        user.setAdmin(true);
        repository.updateUser(user);
        Toast.makeText(this, "User promoted to admin successfully!", Toast.LENGTH_SHORT).show();
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
                TextView welcomeTextView = findViewById(R.id.welcomeTextView);
                welcomeTextView.setText("Welcome, " + user.getUsername());
            }
        });
    }

    private void updateAdminUI(boolean isAdmin) {
        if (isAdmin) {
            binding.manageUsersButton.setVisibility(View.VISIBLE);
            binding.promoteUsersToAdminButton.setVisibility(View.VISIBLE);
            binding.manageUsersButton.setOnClickListener(v -> showUserManagementDialog());
        } else {
            binding.promoteUsersToAdminButton.setVisibility(View.INVISIBLE);
            binding.manageUsersButton.setVisibility(View.INVISIBLE);
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
        //item.setTitle(user.getUsername());
        item.setTitle("Logout");
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