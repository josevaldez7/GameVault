package com.example.gamevault;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExploreActivity extends AppCompatActivity {

    private RecyclerView showsRecyclerView;
    private ShowAdapter showAdapter;
    private Button btnHighMetascore, btnLowMetascore;
    private List<Show> showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        showsRecyclerView = findViewById(R.id.showsRecyclerView);
        btnHighMetascore = findViewById(R.id.btnHighMetascore);
        btnLowMetascore = findViewById(R.id.btnLowMetascore);
        showList = new ArrayList<>();

        showAdapter = new ShowAdapter(showList);
        showsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        showsRecyclerView.setAdapter(showAdapter);

        Retrofit retrofit = APIClient.getRetrofitInstanceForTVMaze();
        APIInterface apiService = retrofit.create(APIInterface.class);

        btnHighMetascore.setOnClickListener(v -> fetchShows(apiService, "popular", 8.0));  // Shows with rating above 8
        btnLowMetascore.setOnClickListener(v -> fetchShows(apiService, "popular", 5.0));   // Shows with rating below 5
    }

    private void fetchShows(APIInterface apiService, String category, double threshold) {
        Call<List<Show>> call = apiService.getShows();
        call.enqueue(new Callback<List<Show>>() {
            @Override
            public void onResponse(Call<List<Show>> call, Response<List<Show>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    showList.clear();  // Clear the previous list
                    List<Show> shows = response.body();

                    for (Show show : shows) {
                        // Get the average rating and filter shows based on it
                        if (show.getRating() != null && show.getRating().getAverage() >= threshold) {
                            showList.add(show);
                        }
                    }

                    // Update the RecyclerView
                    showAdapter.notifyDataSetChanged();
                } else {
                    Log.e("API Error", "Failed to fetch shows. Response code: " + response.code());
                    Toast.makeText(ExploreActivity.this, "Failed to fetch shows", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Show>> call, Throwable t) {
                Toast.makeText(ExploreActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
