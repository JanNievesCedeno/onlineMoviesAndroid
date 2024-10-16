package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MovieBuyTransaction extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    Float allAmount, moneySpent, movieAmount;
    Integer userId,movieID, cuantity, moviesOwned,newMoviesOwned, movieCuantity;
    String radButtonText;
    RadioButton masterCard,Visa;
    TextView buyCardName,buyCardNumber,buyCardExpirationDate,buyAmount;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_buy_transaction);

        masterCard = (RadioButton) findViewById(R.id.radMasterCard);
        Visa = (RadioButton) findViewById(R.id.radVisa);



        buyCardName = (TextView) findViewById(R.id.buyCardName);
        buyCardNumber = (TextView) findViewById(R.id.buyCardNumber);
        buyCardExpirationDate = (TextView) findViewById(R.id.buyCardExpirationDate);
        buyAmount = (TextView) findViewById(R.id.buyAmount);

        Bundle object = getIntent().getExtras();
        movies pelicula = null;

        if(object!=null){
            pelicula = (movies) object.getSerializable("movie");
            buyAmount.setText(pelicula.getMovieCost().toString());
            movieCuantity = pelicula.getMovieAmount();

        }

    }

    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.transactionCancel:
            {
                finish();
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.transactionPay:
            {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyy");
                String dateTime = simpleDateFormat.format(calendar.getTime());
                Bundle object = getIntent().getExtras();
                movies pelicula = null;
                if(object!=null){
                    if (masterCard.isChecked() == true) {
                        radButtonText = masterCard.getText().toString();
                    }

                    if (Visa.isChecked() == true) {
                        radButtonText = Visa.getText().toString();
                    }

                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    userId = sharedPreferences.getInt("userId",0);
                    Log.d("TagMovieBuy","User id: "+userId.toString());
                    pelicula = (movies) object.getSerializable("movie");
                    movieID = pelicula.getMovieId();
                    String [] par = {pelicula.getMovieId().toString()};
                    buyAmount.setText(pelicula.getMovieCost().toString());
                    movieAmount = pelicula.getMovieCost();

                    if ( buyCardNumber.getText().toString().isEmpty() || buyCardExpirationDate.getText().toString().isEmpty() || buyCardName.getText().toString().isEmpty() || buyAmount.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Rellene los datos", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            conn = new ConexionSQLiteHelper(this,"MoviesDataBase",null,1);
                            SQLiteDatabase db = conn.getReadableDatabase();
                            String[] parametro = {userId.toString()};
                            Cursor cursor = db.rawQuery("SELECT "+Constants.FIELD_MOVIESOWNED+","+Constants.FIELD_MONEYSPENT+" FROM "+Constants.TABLE_USERS+" WHERE "+Constants.FIELD_SALES_USERID+" = ? ", parametro);

                            if (cursor.moveToFirst()){
                                moviesOwned = cursor.getInt(0);
                                moneySpent = cursor.getFloat(1);
                            }
                            db = conn.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put(Constants.FIELD_SALES_USERID,userId.toString());
                            values.put(Constants.FIELD_SALES_MOVIEID,movieID.toString());
                            values.put(Constants.FIELD_SALES_SALEDATE,dateTime);
                            values.put(Constants.FIELD_SALES_PAYMENTMETHOD,radButtonText);
                            values.put(Constants.FIELD_SALES_CARDNUMBER,buyCardNumber.getText().toString());
                            values.put(Constants.FIELD_SALES_CARDEXPIRATION,buyCardExpirationDate.getText().toString());
                            values.put(Constants.FIELD_SALES_CARDNAME,buyCardName.getText().toString());
                            values.put(Constants.FIELD_SALES_AMOUNT,buyAmount.getText().toString());


                            db.insert(Constants.TABLE_SALES,null,values);
                            cuantity = 1;
                            newMoviesOwned = cuantity + moviesOwned;
                            allAmount = moneySpent + movieAmount;
                            ContentValues cost = new ContentValues();
                            cost.put(Constants.FIELD_MOVIESOWNED,newMoviesOwned.toString());
                            cost.put(Constants.FIELD_MONEYSPENT,allAmount.toString());
                            db.update(Constants.TABLE_USERS,cost,Constants.FIELD_ID+"=?",parametro);
                            Integer cuantityResult = movieCuantity - cuantity;
                            ContentValues movie = new ContentValues();
                            movie.put(Constants.FIELD_MOVIE_AMOUNT,cuantityResult);
                            db.update(Constants.TABLE_MOVIES,movie,Constants.FIELD_MOVIE_ID+"=?",par);
                            Toast.makeText(getApplicationContext(), "Transaccion completada", Toast.LENGTH_SHORT).show();
                            db.close();
                            conn.close();
                            finish();
                        } catch (Exception e) {
                        }
                    }
                }
                resId = R.raw.heavystomp;
                break;
            }

        }
        if(mediaPlayer!=null)
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this,resId);
        mediaPlayer.start();

    }
}