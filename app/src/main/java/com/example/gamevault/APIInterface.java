package com.example.gamevault;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    // Search for a movie by title in OMDb
    @GET("/")
    Call<Movie> getMovieDetails(
            @Query("t") String title,            // Movie title
            @Query("apikey") String apiKey       // API key (provided by you)
    );

    // Search for a movie by IMDb ID in OMDb
    @GET("/")
    Call<Movie> getMovieByImdbId(
            @Query("i") String imdbId,           // IMDb ID
            @Query("apikey") String apiKey       // API key (provided by you)
    );

    // Search movies by a keyword (returns a list of movies) in OMDb
    @GET("/")
    Call<MovieSearchResponse> searchMovies(
            @Query("s") String searchKeyword,    // Search term (e.g., "Batman")
            @Query("apikey") String apiKey       // API key (provided by you)
    );

    // Fetch TV Shows based on category from TVMaze API
    @GET("shows")
    Call<List<Show>> getShows();
}
