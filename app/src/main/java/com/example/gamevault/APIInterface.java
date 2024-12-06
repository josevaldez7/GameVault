package com.example.gamevault;

//package com.journaldev.retrofitintro;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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



}
