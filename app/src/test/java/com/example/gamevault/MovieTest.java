package com.example.gamevault;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieTest {

    private Movie movie;

    @Before
    public void setUp() {

        movie = new Movie();
    }

    @Test
    public void testTitleGetterSetter() {

        String expectedTitle = "The Matrix";


        movie.setTitle(expectedTitle);
        String actualTitle = movie.getTitle();


        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    public void testYearGetterSetter() {

        String expectedYear = "1999";


        movie.setYear(expectedYear);
        String actualYear = movie.getYear();


        assertEquals(expectedYear, actualYear);
    }

    @Test
    public void testGenreGetterSetter() {

        String expectedGenre = "Action";


        movie.setGenre(expectedGenre);
        String actualGenre = movie.getGenre();


        assertEquals(expectedGenre, actualGenre);
    }

    @Test
    public void testDirectorGetterSetter() {

        String expectedDirector = "The Wachowskis";


        movie.setDirector(expectedDirector);
        String actualDirector = movie.getDirector();


        assertEquals(expectedDirector, actualDirector);
    }

    @Test
    public void testPlotGetterSetter() {

        String expectedPlot = "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.";


        movie.setPlot(expectedPlot);
        String actualPlot = movie.getPlot();


        assertEquals(expectedPlot, actualPlot);
    }

    @Test
    public void testImdbIDGetterSetter() {

        String expectedImdbID = "tt0133093";


        movie.setImdbID(expectedImdbID);
        String actualImdbID = movie.getImdbID();


        assertEquals(expectedImdbID, actualImdbID);
    }

    @Test
    public void testPosterGetterSetter() {

        String expectedPoster = "poster_url.jpg";


        movie.setPoster(expectedPoster);
        String actualPoster = movie.getPoster();


        assertEquals(expectedPoster, actualPoster);
    }
}
