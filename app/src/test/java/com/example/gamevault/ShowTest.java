package com.example.gamevault;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShowTest {

    private Show show;

    @Before
    public void setUp() {

        show = new Show();
    }

    @Test
    public void testNameGetterSetter() {

        String expectedName = "Game of Thrones";


        show.setName(expectedName);
        String actualName = show.getName();


        assertEquals(expectedName, actualName);
    }

    @Test
    public void testGenresGetterSetter() {

        ArrayList<String> expectedGenres = new ArrayList<>();
        expectedGenres.add("Drama");
        expectedGenres.add("Fantasy");


        show.setGenres(expectedGenres);
        ArrayList<String> actualGenres = show.getGenres();


        assertEquals(expectedGenres, actualGenres);
    }

    @Test
    public void testRatingGetterSetter() {

        String expectedRating = "9.3/10";


        show.setRating(expectedRating);
        String actualRating = show.getRating();


        assertEquals(expectedRating, actualRating);
    }

    @Test
    public void testImageGetterSetter() {

        String expectedImage = "image_url.jpg";


        show.setImage(expectedImage);
        String actualImage = show.getImage();


        assertEquals(expectedImage, actualImage);
    }
}
