package com.example.gamevault.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamevault.database.entities.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie... movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM " + GameVaultDataBase.MOVIE_TABLE + " ORDER BY movie_id")
    LiveData<List<Movie>> getALlMovies();


    @Query("DELETE from " + GameVaultDataBase.MOVIE_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GameVaultDataBase.MOVIE_TABLE + " WHERE movieTitle == :movieTitle")
    LiveData<Movie> getMovieByTitle(String movieTitle);

    @Query("SELECT * FROM " + GameVaultDataBase.MOVIE_TABLE + " WHERE movie_id == :movie_id")
    LiveData<Movie> getMovieById(int movie_id);

    @Update
    void update(Movie movie);

}
