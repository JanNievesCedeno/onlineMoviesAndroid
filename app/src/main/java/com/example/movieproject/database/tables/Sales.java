package com.example.movieproject.database.tables;

import android.util.Log;

import java.io.Serializable;
import java.util.Date;

public class Sales implements Serializable {

    private Integer salesId;
    private Integer userId;
    private Integer movieId;
    private String saleDate;
    private String  paymentMethod;
    private String cardNumber;
    private String cardExpiration;
    private String  cardName;
    private Float   amount;

    public Sales(Integer salesId, Integer userId, Integer movieId, String salesDate, String paymentMethod, String cardNumber, String cardExpiration, String cardName, Float amount) {
        this.salesId = salesId;
        this.userId = userId;
        this.movieId = movieId;
        this.saleDate = salesDate;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.cardExpiration = cardExpiration;
        this.cardName = cardName;
        this.amount = amount;
    }

    public Sales(){

    }

    public Integer getSalesId() {
        Log.d("TagSales","Id de venta "+salesId);
        return salesId;
    }

    public void setSalesId(Integer salesId) {
        this.salesId = salesId;
    }

    public Integer getUserId() {
        Log.d("TagSales","Id de usuario "+userId);
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getSalesDate() {
        return saleDate;
    }

    public void setSalesDate(String salesDate) {
        this.saleDate = salesDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
