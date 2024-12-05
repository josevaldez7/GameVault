package com.example.gamevault;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gamevault.database.GameVaultRepository;
import com.example.gamevault.database.entities.User;
import com.example.gamevault.databinding.ActivityChangeInfoBinding;



public class ChangeInfoActivity extends AppCompatActivity {

    private ActivityChangeInfoBinding binding;
    private GameVaultRepository repository;
    private User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        binding = ActivityChangeInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GameVaultRepository.getRepository(getApplication());

        int loggedInUserId = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
                .getInt(getString(R.string.preference_userId_key), -1);

        if (loggedInUserId == -1) {
            Toast.makeText(this, "Error: No logged-in user found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        repository.getUserByUserId(loggedInUserId).observe(this, user -> {
            if (user != null) {
                loggedInUser = user;
            }
        });


        binding.backToMainButton.setOnClickListener(view -> {
            Intent intent = new Intent(ChangeInfoActivity.this, MainActivity.class);
            startActivity(intent);
        });
        binding.confirmChangesButton.setOnClickListener(view -> updateUserInformation());
    }

    private void updateUserInformation() {
        if (loggedInUser == null) {
            Toast.makeText(this, "Error: User data not loaded.", Toast.LENGTH_SHORT).show();
            return;
        }
        String newUsername = binding.changeUserName.getText().toString().trim();
        String newPassword = binding.changePassword.getText().toString().trim();

        if (newUsername.isEmpty() && newPassword.isEmpty()) {
            Toast.makeText(this, "Please provide new information.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newUsername.isEmpty() && !newUsername.equals(loggedInUser.getUsername())) {
            repository.getUserByUserName(newUsername).observe(this, existingUser -> {
                if (existingUser != null) {
                    Toast.makeText(this, "Username already taken!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    loggedInUser.setUsername(newUsername);

                    if (!newPassword.isEmpty()) {
                        loggedInUser.setPassword(newPassword);
                    }
                    repository.updateUser(loggedInUser);
                    Toast.makeText(this, "Information updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            if (!newPassword.isEmpty()) {
                loggedInUser.setPassword(newPassword);
            }
            repository.updateUser(loggedInUser);
            Toast.makeText(this, "Information updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
