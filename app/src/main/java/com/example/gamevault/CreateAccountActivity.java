package com.example.gamevault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.gamevault.database.GameVaultRepository;
import com.example.gamevault.database.entities.User;
import com.example.gamevault.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {
    private ActivityCreateAccountBinding binding;
    private GameVaultRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GameVaultRepository.getRepository(getApplication());

        binding.createAccountButton.setOnClickListener(view -> createAccount());

        binding.backToLoginButton.setOnClickListener(view -> navigateToLogin());
    }

    private void createAccount() {
        String username = binding.usernameEditText.getText().toString();
        String password = binding.passwordEditText.getText().toString();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString();


        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields must be completed.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }

        LiveData<User> existingUser = repository.getUserByUserName(username);
        existingUser.observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(username, password);
                repository.insertUser(newUser);
                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void navigateToLogin() {
        Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

