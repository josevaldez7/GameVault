package com.example.gamevault;

import java.util.List;

public class MovieSearchResponse {

    private String Response;
    private List<Movie> Search;  // List of movies
    private String totalResults;

    // Getters and Setters
    public List<Movie> getSearch() {
        return Search;
    }

    public void setSearch(List<Movie> search) {
        Search = search;
    }
}

