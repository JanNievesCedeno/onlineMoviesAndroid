package com.example.movieproject.database.tables;

import java.io.Serializable;

public class users implements Serializable {

    private Integer userId;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private Integer moviesOwned;
    private Float moneySpent;

    public users(Integer userId, String name, String lastName,  String username, String password, Integer moviesOwned, Float moneySpent) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.moviesOwned = moviesOwned;
        this.moneySpent = moneySpent;


    }

    public users(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMoviesOwned() {
        return moviesOwned;
    }

    public void setMoviesOwned(Integer moviesOwned) {
        this.moviesOwned = moviesOwned;
    }

    public Float getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(Float moneySpent) {
        this.moneySpent = moneySpent;
    }


}
