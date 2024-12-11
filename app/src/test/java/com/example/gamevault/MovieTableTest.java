package com.example.gamevault.database.entities;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTableTest {

    private Movie movie;

    @Before
    public void setUp() {

        movie = new Movie(1, "Inception");
    }

    @Test
    public void testGetMovieId() {

        int movieId = movie.getMovie_id();


        assertEquals(0, movieId);
    }

    @Test
    public void testSetMovieId() {

        movie.setMovie_id(5);


        assertEquals(5, movie.getMovie_id());
    }

    @Test
    public void testGetFavId() {

        int favId = movie.getFav_id();


        assertEquals(1, favId);
    }

    @Test
    public void testSetFavId() {

        movie.setFav_id(10);


        assertEquals(10, movie.getFav_id());
    }

    @Test
    public void testGetMovieTitle() {

        String movieTitle = movie.getMovieTitle();


        assertEquals("Inception", movieTitle);  // The movieTitle should be "Inception"
    }

    @Test
    public void testSetMovieTitle() {

        movie.setMovieTitle("The Dark Knight");


        assertEquals("The Dark Knight", movie.getMovieTitle());
    }

    @Test
    public void testEquals_SameObject() {

        Movie sameMovie = movie;


        assertTrue(movie.equals(sameMovie));
    }

    @Test
    public void testEquals_DifferentObject() {

        Movie anotherMovie = new Movie(1, "Inception");


        assertTrue(movie.equals(anotherMovie));
    }

    @Test
    public void testEquals_DifferentMovieTitle() {

        Movie differentTitleMovie = new Movie(1, "The Dark Knight");


        assertFalse(movie.equals(differentTitleMovie));
    }

    @Test
    public void testEquals_DifferentFavId() {

        Movie differentFavMovie = new Movie(2, "Inception");


        assertFalse(movie.equals(differentFavMovie));
    }

    @Test
    public void testHashCode() {

        int hashCode = movie.hashCode();


        Movie anotherMovie = new Movie(1, "Inception");
        assertEquals(hashCode, anotherMovie.hashCode());
    }

    @Test
    public void testConstructor() {

        Movie newMovie = new Movie(2, "The Matrix");


        assertEquals(2, newMovie.getFav_id());
        assertEquals("The Matrix", newMovie.getMovieTitle());
        assertEquals(0, newMovie.getMovie_id());
    }
}
