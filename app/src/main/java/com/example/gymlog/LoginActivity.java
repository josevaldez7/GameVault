package com.example.gymlog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gymlog.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Login button redirects to LoginFormActivity
        binding.loginButton.setOnClickListener(view -> {
            Intent intent = LoginFormActivity.loginFormIntentFactory(LoginActivity.this);
            startActivity(intent);
        });

        // Create Account button can redirect to a sign-up activity
        binding.createAccountButton.setOnClickListener(view -> {
            // Add logic for creating a new account
        });
    }
}
