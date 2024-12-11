package com.example.gamevault;
//package com.journaldev.retrofitintro;

//import com.journaldev.retrofitintro.pojo.MultipleResource;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BASE_URL = "https://www.omdbapi.com/?t=";
    private static final String SHOW_URL = "https://api.tvmaze.com/shows";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                .build();
    }

    public static Retrofit getRetrofitInstance1() {
        return new Retrofit.Builder()
                .baseUrl(SHOW_URL)
                .addConverterFactory(GsonConverterFactory.create())  // Gson converter for JSON parsing
                .build();
    }
}
