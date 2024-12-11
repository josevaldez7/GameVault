package com.example.gamevault;

public class Show {
    private String name;
    private Rating rating;
     private String Poster;
    // Getters and setters
    public String getName() {
        return name;
    }
public String getPoster()
{
    return Poster;
}
    public void setPoster(String poster) {
        this.Poster = poster;
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
