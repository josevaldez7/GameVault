package com.example.gamevault;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;

public class BrowseActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button homeButton = findViewById(R.id.homeButton);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(BrowseActivity.this, MainActivity.class);

            // Start the MainActivity
            startActivity(intent);

            // Optional: Finish the current activity to remove it from the back stack
            finish();
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        EditText searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);


        searchButton.setOnClickListener(v -> {
            String searchQuery = searchEditText.getText().toString().trim();
            if (!searchQuery.isEmpty()) {
                fetchMovies(searchQuery);
            } else {
                Toast.makeText(this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchMovies(String searchQuery) {
        String apiKey = "f7329d4a";  // Replace with your OMDB API key

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiService = retrofit.create(APIInterface.class);

        Call<MovieSearchResponse> call = apiService.searchMovies(searchQuery, apiKey);
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movieList = response.body().getSearch();
                    movieAdapter = new MovieAdapter(movieList);
                    recyclerView.setAdapter(movieAdapter);
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Toast.makeText(BrowseActivity.this, "Failed to load movies", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent browseIntentFactory(MainActivity mainActivity) {
        return new Intent(mainActivity, BrowseActivity.class);
    }
}