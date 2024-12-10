package com.example.gamevault;

import java.util.ArrayList;

public class Show {
    private String name;
    private Rating rating;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public static class Rating {
        private float average;

        // Getter and setter
        public float getAverage() {
            return average;
        }

        public void setAverage(float average) {
            this.average = average;
        }
    }
}