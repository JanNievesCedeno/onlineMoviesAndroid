package com.example.movieproject.database.tables;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class movies implements Serializable {

    private Integer movieId;
    private Integer movieAmount;
    private String  movieName;
    private String  movieYear;
    private String  movieGenre;
    private String  movieDescription;
    private Float   movieCost;
    private String  moviePicture;
    private String  movieTrailer;

    public movies(Integer movieId, Integer movieAmount, String movieName, String movieYear, String movieGenre, String movieDescription, Float movieCost, String moviePicture, String movieTrailer) {
        this.movieId = movieId;
        this.movieAmount = movieAmount;
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieGenre = movieGenre;
        this.movieDescription = movieDescription;
        this.movieCost = movieCost;
        this.moviePicture = moviePicture;
        this.movieTrailer = movieTrailer;
    }

    public movies(){

    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getMovieAmount() {
        return movieAmount;
    }

    public void setMovieAmount(Integer movieAmount) {
        this.movieAmount = movieAmount;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public Float getMovieCost() {
        return movieCost;
    }

    public void setMovieCost(Float movieCost) {
        this.movieCost = movieCost;
    }

    public String getMoviePicture() {
        return moviePicture;
    }

    public void setMoviePicture(String moviePicture) {
        this.moviePicture = moviePicture;
    }

    public String getMovieTrailer() {
        return movieTrailer;
    }

    public void setMovieTrailer(String movieTrailer) {
        this.movieTrailer = movieTrailer;
    }
}
