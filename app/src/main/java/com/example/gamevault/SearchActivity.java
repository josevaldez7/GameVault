package com.example.gamevault;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gamevault.adapter.MovieAdapter;
import com.example.gamevault.api.APIClient;
import com.example.gamevault.api.APIInterface;
import com.example.gamevault.model.Movie;
import com.example.gamevault.model.MovieSearchResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private static final String API_KEY = "f7329d4a"; // Replace with your OMDb API Key
    private EditText searchEditText;
    private RecyclerView searchResultsRecyclerView;
    private TextView movieTitleTextView;
    private TextView movieDescriptionTextView;
    private ImageView moviePosterImageView;
    private MovieAdapter movieAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        Button homeButton = findViewById(R.id.homeButton);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieDescriptionTextView = findViewById(R.id.movieDescriptionTextView);
        moviePosterImageView = findViewById(R.id.moviePosterImageView);
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);

        // Set up RecyclerView with an empty adapter
        movieAdapter = new MovieAdapter(new ArrayList<>());
        searchResultsRecyclerView.setAdapter(movieAdapter);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up Retrofit
        Retrofit retrofit = APIClient.getRetrofitInstance();
        APIInterface apiService = retrofit.create(APIInterface.class);

        // Home Button click listener
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Optional: Finish this activity
        });

        // Search Button click listener
        searchButton.setOnClickListener(v -> {
            String movieName = searchEditText.getText().toString().trim();

            if (TextUtils.isEmpty(movieName)) {
                Toast.makeText(SearchActivity.this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
                return;
            }

            movieName = movieName.replace(" ", "+"); // Format movie name for API query

            // Make API call to fetch movie details
            fetchMovieDetails(apiService, movieName);

            // Alternatively, make an API call to fetch a list of movies
            fetchMovieList(apiService, movieName);
        });
    }

    /**
     * Fetch detailed information about a single movie and update the UI.
     */
    private void fetchMovieDetails(APIInterface apiService, String movieName) {
        Call<Movie> call = apiService.getMovieDetails(movieName, API_KEY);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie movie = response.body();

                    // Update UI with movie details
                    movieTitleTextView.setText("Title: " + movie.getTitle());
                    movieDescriptionTextView.setText("Description: " + movie.getPlot());
                    moviePosterImageView.setVisibility(View.VISIBLE);

                    // Load poster image using Glide
                    Glide.with(SearchActivity.this)
                            .load(movie.getPoster()) // URL for the poster
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.error_image)
                            .into(moviePosterImageView);
                } else {
                    Toast.makeText(SearchActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Fetch a list of movies matching the search query and display them in the RecyclerView.
     */
    private void fetchMovieList(APIInterface apiService, String movieName) {
        Call<MovieSearchResponse> call = apiService.searchMovies(movieName, API_KEY);
        call.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getMovies() != null) {
                    // Update RecyclerView with the list of movies
                    movieAdapter.updateData(response.body().getMovies());
                } else {
                    Toast.makeText(SearchActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
                Log.e(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Intent searchIntentFactory(MainActivity mainActivity) {
        return new Intent(mainActivity, SearchActivity.class);
    }
}
