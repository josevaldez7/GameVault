package com.example.gamevault;

//package com.journaldev.retrofitintro;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.List; // Import for List

import com.example.gamevault.models.Game; // Import for your Game model class


public interface APIInterface {

    // Search for a movie by title
    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("t") String title,            // Movie title
            @Query("apikey") String apiKey       // API key (provided by you)
    );

    // Search for a movie by IMDb ID
    @GET("/")
    Call<Movie> getMovieByImdbId(
            @Query("i") String imdbId,           // IMDb ID
            @Query("apikey") String apiKey       // API key (provided by you)
    );

    // Search movies by a keyword (returns a list of movies)
    @GET("/")
    Call<MovieSearchResponse> searchMovies(
            @Query("s") String searchKeyword,    // Search term (e.g., "Batman")
            @Query("apikey") String apiKey       // API key (provided by you)
    );


    // Fetch games by title
    @GET("/")
    Call<List<Game>> searchGames(@Query("t") String title);

    // Fetch trending games (replace with actual API endpoint)
    @GET("trending-games")
    Call<List<Game>> getTrendingGames();

}
