package com.example.gamevault;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExploreActivity extends AppCompatActivity {

    private ListView genreListView;
    private HashMap<String, List<String>> genreRecommendations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        genreListView = findViewById(R.id.genreListView);

        // Sample genres and game recommendations
        genreRecommendations = new HashMap<>();
        genreRecommendations.put("Action", List.of("Game 1", "Game 2", "Game 3"));
        genreRecommendations.put("Adventure", List.of("Game 4", "Game 5", "Game 6"));
        genreRecommendations.put("RPG", List.of("Game 7", "Game 8", "Game 9"));
        genreRecommendations.put("Sports", List.of("Game 10", "Game 11", "Game 12"));

        List<HashMap<String, String>> genreList = new ArrayList<>();
        for (String genre : genreRecommendations.keySet()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("genre", genre);
            genreList.add(map);
        }

        // Use SimpleAdapter
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                genreList,
                R.layout.genre_list_item,
                new String[] {"genre"},
                new int[] {R.id.genreName}
        );

        genreListView.setAdapter(adapter);

        genreListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedGenre = (String) ((HashMap) genreList.get(position)).get("genre");
            List<String> recommendations = genreRecommendations.get(selectedGenre);

            String recommendationString = String.join(", ", recommendations);

            Toast.makeText(ExploreActivity.this,
                    "Recommendations for " + selectedGenre + ": " + recommendationString,
                    Toast.LENGTH_LONG).show();
        });
    }
}
