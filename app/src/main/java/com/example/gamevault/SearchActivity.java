package com.example.gamevault;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private static final String API_KEY = "f7329d4a";
    private EditText searchEditText;
    private TextView movieTitleTextView;
    private TextView movieDescriptionTextView;
    private ImageView moviePosterImageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchEditText);
        Button searchButton = findViewById(R.id.searchButton);
        Button homeButton = findViewById(R.id.homeButton);
        movieTitleTextView = findViewById(R.id.movieTitleTextView);
        movieDescriptionTextView = findViewById(R.id.movieDescriptionTextView);
        moviePosterImageView = findViewById(R.id.moviePosterImageView);

        Retrofit retrofit = APIClient.getRetrofitInstance();
        APIInterface apiService = retrofit.create(APIInterface.class);

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        searchButton.setOnClickListener(v -> {
            String movieName = searchEditText.getText().toString().trim().replace(" ", "+");

            if (TextUtils.isEmpty(movieName)) {
                Toast.makeText(SearchActivity.this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
                return;
            }

            Call<Movie> call = apiService.getMovieDetails(movieName, API_KEY);
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Movie movie = response.body();
                        movieTitleTextView.setText("Title: " + movie.getTitle());
                        movieDescriptionTextView.setText("Description: " + movie.getPlot());
                        Glide.with(SearchActivity.this)
                                .load(movie.getPoster())
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
        });
    }

    public static Intent searchIntentFactory(Context context) {
        return new Intent(context, SearchActivity.class);
    }
}
