package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.Sales;
import com.example.movieproject.database.tables.users;

import java.math.BigDecimal;

public class CrudSales extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    TextView dataId, dataUserId, dataMovieId, dataDate, dataPaymentMethod, dataCardNumber, dataCardExpiration, dataCardName, dataAmount;
    ConexionSQLiteHelper conn;
    String saleId, userId, movieId;
    Integer cuantity, userMoviesOwned, moviesOwnedUpdate, movieAmount, movieAmoutUpdate;
    Float amount, userMoneySpent, moneySpentUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_sales);

        dataId = (TextView) findViewById(R.id.crudSalesId);
        dataUserId = (TextView) findViewById(R.id.crudSalesUserId);
        dataMovieId = (TextView) findViewById(R.id.crudSalesMovieId);
        dataDate = (TextView) findViewById(R.id.crudSalesDate);
        dataPaymentMethod = (TextView) findViewById(R.id.crudSalesPaymentMethod);
        dataCardNumber = (TextView) findViewById(R.id.crudSalesCardNumber);
        dataCardExpiration = (TextView) findViewById(R.id.crudSalesCardExpiration);
        dataCardName = (TextView) findViewById(R.id.crudSalesCardName);
        dataAmount = (TextView) findViewById(R.id.crudSalesAmount);

        Bundle object = getIntent().getExtras();
        Sales venta = null;

        if (object != null) {
            venta = (Sales) object.getSerializable("sale");
            dataId.setText(venta.getSalesId().toString());
            saleId = venta.getSalesId().toString();
            dataUserId.setText(venta.getUserId().toString());
            userId = venta.getUserId().toString();
            dataMovieId.setText(venta.getMovieId().toString());
            movieId = venta.getMovieId().toString();
            dataDate.setText(venta.getSalesDate().toString());
            dataPaymentMethod.setText(venta.getPaymentMethod().toString());
            dataCardNumber.setText(venta.getCardNumber().toString());
            dataCardExpiration.setText(venta.getCardExpiration().toString());
            dataCardName.setText(venta.getCardName().toString());
            dataAmount.setText(venta.getAmount().toString());
            Log.d("Tag", "Se encontro el objeto sale");
        } else {
            Log.d("Tag", "No se encontro el objeto sale");
        }

    }

    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.crudSalesDelete: {

                try {
                    getSaleAmount();
                    updateUserInfo();
                    updateMovieInfo();
                    conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {dataId.getText().toString()};
                    db.delete(Constants.TABLE_SALES, Constants.FIELD_SALES_ID + "=?", parametros);
                    Toast.makeText(getApplicationContext(), "Se elimino correctamente la venta", Toast.LENGTH_SHORT).show();
                    db.close();
                    conn.close();
                    finish();
                } catch (Exception e) {
                }
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.crudSalesUpdate: {

                try {
                    conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {dataId.getText().toString()};
                    ContentValues values = new ContentValues();
                    values.put(Constants.FIELD_SALES_ID, dataId.getText().toString());
                    values.put(Constants.FIELD_SALES_USERID, dataUserId.getText().toString());
                    values.put(Constants.FIELD_SALES_MOVIEID, dataMovieId.getText().toString());
                    values.put(Constants.FIELD_SALES_SALEDATE, dataDate.getText().toString());
                    values.put(Constants.FIELD_SALES_PAYMENTMETHOD, dataPaymentMethod.getText().toString());
                    values.put(Constants.FIELD_SALES_CARDNUMBER, dataCardNumber.getText().toString());
                    values.put(Constants.FIELD_SALES_CARDEXPIRATION, dataCardExpiration.getText().toString());
                    values.put(Constants.FIELD_SALES_CARDNAME, dataCardName.getText().toString());
                    values.put(Constants.FIELD_SALES_AMOUNT, dataAmount.getText().toString());
                    db.update(Constants.TABLE_SALES, values, Constants.FIELD_SALES_ID + "=?", parametros);
                    Toast.makeText(getApplicationContext(), "Ya se actualizo correctamente la venta", Toast.LENGTH_SHORT).show();
                    db.close();
                    conn.close();
                    finish();
                } catch (Exception e) {

                }

                resId = R.raw.heavystomp;
                break;
            }

        }
        if (mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, resId);
        mediaPlayer.start();

    }

    private void updateMovieInfo() {
        conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametro = {movieId};
        Cursor cursor = db.rawQuery("SELECT " + Constants.FIELD_MOVIE_AMOUNT + " FROM " + Constants.TABLE_MOVIES + " WHERE " + Constants.FIELD_MOVIE_ID + " = ? ", parametro);
        cursor.moveToFirst();

        cuantity = 1;
        movieAmount = cursor.getInt(0);
        movieAmoutUpdate = movieAmount + cuantity;

        ContentValues values = new ContentValues();
        values.put(Constants.FIELD_MOVIE_AMOUNT, movieAmoutUpdate);
        db.update(Constants.TABLE_MOVIES, values, Constants.FIELD_MOVIE_ID + "=?", parametro);

    }

    private void updateUserInfo() {
        conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametro = {userId};
        Cursor cursor = db.rawQuery("SELECT " + Constants.FIELD_MOVIESOWNED + "," + Constants.FIELD_MONEYSPENT + " FROM " + Constants.TABLE_USERS + " WHERE " + Constants.FIELD_ID + " = ? ", parametro);
        cursor.moveToFirst();
        cuantity = 1;
        userMoviesOwned = cursor.getInt(0);
        userMoneySpent = cursor.getFloat(1);

        moneySpentUpdate = userMoneySpent - amount;
        moviesOwnedUpdate = userMoviesOwned - cuantity;

        ContentValues values = new ContentValues();
        values.put(Constants.FIELD_MOVIESOWNED, moviesOwnedUpdate.toString());
        values.put(Constants.FIELD_MONEYSPENT, String.format("%.2f", moneySpentUpdate));
        db.update(Constants.TABLE_USERS, values, Constants.FIELD_ID + "=?", parametro);
        db.close();
        conn.close();

    }

    private void getSaleAmount() {
        conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametro = {saleId};
        String[] campo = {Constants.FIELD_SALES_AMOUNT};

//        Cursor cursor = db.rawQuery("SELECT "+Constants.FIELD_SALES_AMOUNT+" FROM "+Constants.TABLE_SALES+" WHERE "+Constants.FIELD_SALES_ID+" = ? ", parametro);
//        amount = cursor.getFloat(0);

        Cursor cursor = db.query(Constants.TABLE_SALES, campo, Constants.FIELD_SALES_ID + "=?", parametro, null, null, null);
        cursor.moveToFirst();
        amount = cursor.getFloat(0);
        Log.d("TagCrudSales", amount.toString());
        db.close();
        conn.close();

    }
}