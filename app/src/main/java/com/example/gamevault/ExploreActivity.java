package com.example.gamevault;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamevault.APIClient;
import com.example.gamevault.APIInterface;
import com.example.gamevault.models.Game;
import com.example.gamevault.adapters.GameAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView recyclerViewExplore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        // Initialize RecyclerView
        recyclerViewExplore = findViewById(R.id.recyclerViewExplore);
        recyclerViewExplore.setLayoutManager(new LinearLayoutManager(this));

        // Load explore games
        loadExploreGames();
    }

    private void loadExploreGames() {
        // Create Retrofit instance
        APIInterface apiInterface = APIClient.getRetrofitInstance().create(APIInterface.class);

        // Fetch trending games
        Call<List<Game>> call = apiInterface.getTrendingGames();
        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Display the games in RecyclerView
                    displayExploreGames(response.body());
                } else {
                    Toast.makeText(ExploreActivity.this, "No trending games found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Toast.makeText(ExploreActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayExploreGames(List<Game> games) {
        // Set up the RecyclerView with the fetched game list
        GameAdapter adapter = new GameAdapter(games);
        recyclerViewExplore.setAdapter(adapter);
    }
}
