package com.example.myapplication;

public class Movie {
    String name;
    String year;
    String rating;

    public Movie(String name, String year, String rating) {
        this.name = name;
        this.year = year;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }
}
