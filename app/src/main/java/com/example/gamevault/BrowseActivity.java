package com.example.gamevault;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.widget.Button;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.gamevault.databinding.ActivityBrowseBinding;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class BrowseActivity extends AppCompatActivity {


    private TextView browse;
    private RecyclerView showsRecyclerView;
    private ImageView showImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        // Initialize views
        Button homeButton = findViewById(R.id.homeButton);
        showsRecyclerView = findViewById(R.id.recyclerView);
        browse = findViewById(R.id.browse);


        // Set up Retrofit
        Retrofit retrofit = APIClient.getRetrofitInstance1();
        APIInterface apiService = retrofit.create(APIInterface.class);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BrowseActivity.this, MainActivity.class);

            // Start the MainActivity
            startActivity(intent);

            // Optional: Finish the current activity to remove it from the back stack
            finish();
        });

    }


    public static Intent browseIntentFactory(MainActivity mainActivity) {
        return new Intent(mainActivity, BrowseActivity.class);
    }
}