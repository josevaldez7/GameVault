package com.example.gamevault.database.entities;



import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.gamevault.database.GameVaultDataBase;

import java.util.Objects;

@Entity(
        tableName = GameVaultDataBase.MOVIE_TABLE,
        foreignKeys = @ForeignKey(
                entity = Favorites.class,
                parentColumns = "favorite_id",
                childColumns = "movie_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Movie {

    @PrimaryKey(autoGenerate = true)
    private int movie_id;
    private int fav_id;
    private String movieTitle;

    public Movie(int fav_id, String movieTitle) {
        this.fav_id = fav_id;
        this.movieTitle = movieTitle;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movie_id == movie.movie_id && fav_id == movie.fav_id && Objects.equals(movieTitle, movie.movieTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie_id, fav_id, movieTitle);
    }
}
