package com.example.gamevault;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamevault.adapter.GenreAdapter;
import com.example.gamevault.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView genreRecyclerView;
    private HashMap<String, List<String>> genreRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        genreRecyclerView = findViewById(R.id.genreRecyclerView);

        // Sample genres and game recommendations
        genreRecommendations = new HashMap<>();
        genreRecommendations.put("Action", List.of("Game 1", "Game 2", "Game 3"));
        genreRecommendations.put("Adventure", List.of("Game 4", "Game 5", "Game 6"));
        genreRecommendations.put("RPG", List.of("Game 7", "Game 8", "Game 9"));
        genreRecommendations.put("Sports", List.of("Game 10", "Game 11", "Game 12"));

        // Prepare genre list
        List<String> genres = new ArrayList<>(genreRecommendations.keySet());

        // Set up RecyclerView
        GenreAdapter adapter = new GenreAdapter(genres, genreRecommendations, this::showRecommendations);
        genreRecyclerView.setAdapter(adapter);
        genreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Displays a Toast message with recommendations for the selected genre.
     *
     * @param genre The selected genre.
     */
    private void showRecommendations(String genre) {
        List<String> recommendations = genreRecommendations.get(genre);
        if (recommendations != null) {
            String recommendationString = String.join(", ", recommendations);
            Toast.makeText(this,
                    "Recommendations for " + genre + ": " + recommendationString,
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No recommendations available.", Toast.LENGTH_SHORT).show();
        }
    }
}
