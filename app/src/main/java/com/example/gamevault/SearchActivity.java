package com.example.gamevault;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.bumptech.glide.Glide;
import com.example.gamevault.databinding.ActivitySearchBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private static final String API_KEY = "f7329d4a"; // Replace with your OMDb API Key
    private EditText searchEditText;
    private TextView movieTitleTextView;
    private TextView movieDescriptionTextView;
    private ImageView moviePosterImageView;

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
        moviePosterImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        // Set up Retrofit
        Retrofit retrofit = APIClient.getRetrofitInstance();
        APIInterface apiService = retrofit.create(APIInterface.class);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);

            // Start the MainActivity
            startActivity(intent);

            // Optional: Finish the current activity to remove it from the back stack
            finish();
        });

        // Button click listener to trigger movie search
        searchButton.setOnClickListener(v -> {
            String movieName = searchEditText.getText().toString().trim();
            movieName = movieName.replace(" ", "+");



            if (TextUtils.isEmpty(movieName)) {
                Toast.makeText(SearchActivity.this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
                return;
            }

            // Make API call
            Call<Movie> call = apiService.getMovieDetails(movieName, API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Movie movie = response.body();

                        // Display movie details in UI
                        movieTitleTextView.setText("Title: " + movie.getTitle());
                        movieDescriptionTextView.setText("Description: " + movie.getPlot());

                        // Use Glide to load the poster image into the ImageView
                        Glide.with(SearchActivity.this)
                                .load(movie.getPoster())  // URL for the poster
                                .into(moviePosterImageView);
                    } else {
                        // Handle error
                        Toast.makeText(SearchActivity.this, "Movie not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    // Handle failure
                    Log.e("SearchActivity", "API call failed: " + t.getMessage());
                    Toast.makeText(SearchActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }



    public static Intent searchIntentFactory(MainActivity mainActivity) {
        return new Intent(mainActivity, SearchActivity.class);
    }
}