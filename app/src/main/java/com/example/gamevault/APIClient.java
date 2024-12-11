package com.example.gamevault;
//package com.journaldev.retrofitintro;

//import com.journaldev.retrofitintro.pojo.MultipleResource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String OMDB_BASE_URL = "https://www.omdbapi.com/";
    private static final String TVMAZE_BASE_URL = "https://api.tvmaze.com/";

    // Retrofit instance for OMDb API
    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(OMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                .build();
    }

    // Retrofit instance for TVMaze API
    public static Retrofit getRetrofitInstanceForTVMaze() {
        return new Retrofit.Builder()
                .baseUrl(TVMAZE_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                .build();
    }

    // Get API interface instance for OMDb
    public static APIInterface getOMDbService() {
        return getRetrofitInstance().create(APIInterface.class);
    }

    // Get API interface instance for TVMaze
    public static APIInterface getTVMazeService() {
        return getRetrofitInstanceForTVMaze().create(APIInterface.class);
    }
}