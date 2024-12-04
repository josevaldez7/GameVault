package com.example.gamevault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.gamevault.database.GameVaultRepository;
import com.example.gamevault.database.entities.User;
import com.example.gamevault.databinding.ActivityLoginFormBinding;

public class LoginFormActivity extends AppCompatActivity {

    private ActivityLoginFormBinding binding;
    private GameVaultRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = GameVaultRepository.getRepository(getApplication());

        binding.loginButton.setOnClickListener(view -> verifyUser());

        binding.BacktoHome.setOnClickListener(view -> {
            Intent intent = LoginActivity.loginIntentFactory(LoginFormActivity.this);
            startActivity(intent);
        });

    }

    private void verifyUser() {
        String username = binding.userNameLoginEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username should not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordLoginEditText.getText().toString();
                if (password.equals(user.getPassword())) {
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("%s is not a valid username.", username));
                binding.userNameLoginEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginFormIntentFactory(Context context) {
        return new Intent(context, LoginFormActivity.class);
    }
}
