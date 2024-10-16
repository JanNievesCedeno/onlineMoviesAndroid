package com.example.movieproject.database;

public class Constants {

    // Constants for users table

    public static String TABLE_USERS = "users";
    public static String FIELD_ID = "userId";
    public static String FIELD_NAME = "name";
    public static String FIELD_LASTNAME = "lastName";
    public static String FIELD_USERNAME = "username";
    public static String FIELD_PASSWORD = "password";
    public static String FIELD_MOVIESOWNED = "moviesOwned";
    public static String FIELD_MONEYSPENT = "moneySpent";

    public static final String CREATE_TABLE_USERS=
            "CREATE TABLE "+TABLE_USERS+" ("+FIELD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME+" TEXT NOT NULL, "+FIELD_LASTNAME+" TEXT NOT NULL, "+FIELD_USERNAME+" TEXT NOT NULL, "+FIELD_PASSWORD+" TEXT NOT NULL, "+FIELD_MOVIESOWNED+" INTEGER DEFAULT 0, "+FIELD_MONEYSPENT+" REAL (4,2) DEFAULT 0.0)";


    // Constants for movies table

    public static String TABLE_MOVIES = "movies";
    public static String FIELD_MOVIE_ID = "movieID";
    public static String FIELD_MOVIE_AMOUNT = "movieAmount";
    public static String FIELD_MOVIE_NAME = "movieName";
    public static String FIELD_MOVIE_YEAR = "movieYear";
    public static String FIELD_MOVIE_GENRE = "movieGenre";
    public static String FIELD_MOVIE_DESCRIPTION = "movieDescription";
    public static String FIELD_MOVIE_COST = "movieCost";
    public static String FIELD_MOVIE_PICTURE = "moviePicture";
    public static String FIELD_MOVIE_TRAILER = "movieTrailer";

    public static final String CREATE_TABLE_MOVIES=
            "CREATE TABLE "+TABLE_MOVIES+" ("+FIELD_MOVIE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_MOVIE_AMOUNT+" INTEGER NOT NULL, "+FIELD_MOVIE_NAME+" TEXT NOT NULL, "+FIELD_MOVIE_YEAR+" INTEGER(4) NOT NULL, "+FIELD_MOVIE_GENRE+" TEXT NOT NULL, "+FIELD_MOVIE_DESCRIPTION+" TEXT NOT NULL, "+FIELD_MOVIE_COST+" REAL (4,2) NOT NULL, "+FIELD_MOVIE_PICTURE+" TEXT NOT NULL, "+FIELD_MOVIE_TRAILER+" TEXT NOT NULL)";



    //Constants for sales table

    public static String TABLE_SALES = "sales";
    public static String FIELD_SALES_ID = "saleId";
    public static String FIELD_SALES_USERID = "userId";
    public static String FIELD_SALES_MOVIEID = "movieId";
    public static String FIELD_SALES_SALEDATE = "saleDate";
    public static String FIELD_SALES_PAYMENTMETHOD = "paymentMethod";
    public static String FIELD_SALES_CARDNUMBER = "cardNumber";
    public static String FIELD_SALES_CARDEXPIRATION = "cardExpiration";
    public static String FIELD_SALES_CARDNAME = "cardName";
    public static String FIELD_SALES_AMOUNT = "amount";

    public static final String CREATE_TABLE_SALES=
            "CREATE TABLE "+TABLE_SALES+" ("+FIELD_SALES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+FIELD_SALES_USERID+" INTEGER NOT NULL, "+FIELD_SALES_MOVIEID+" INTEGER NOT NULL, "+FIELD_SALES_SALEDATE+" TEXT NOT NULL, "+FIELD_SALES_PAYMENTMETHOD+" TEXT NOT NULL, "+FIELD_SALES_CARDNUMBER+" TEXT NOT NULL, "+FIELD_SALES_CARDEXPIRATION+" TEXT NOT NULL, "+FIELD_SALES_CARDNAME+" TEXT NOT NULL, "+FIELD_SALES_AMOUNT+" REAL (4,2) NOT NULL, FOREIGN KEY ("+Constants.FIELD_SALES_USERID+") REFERENCES "+TABLE_USERS+"("+Constants.FIELD_ID+"), FOREIGN KEY ("+Constants.FIELD_SALES_MOVIEID+") REFERENCES "+Constants.TABLE_MOVIES+" ("+Constants.FIELD_MOVIE_ID+") )";

}
