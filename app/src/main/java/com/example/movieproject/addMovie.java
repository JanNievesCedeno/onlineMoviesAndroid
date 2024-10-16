package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;

public class addMovie extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    TextView movieAmount, movieName, movieYear, movieGenre, movieDescription, movieCost, moviePicture, movieTrailer;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        movieAmount = (TextView) findViewById(R.id.addMovieAmount);
        movieName = (TextView) findViewById(R.id.addMovieName);
        movieYear = (TextView) findViewById(R.id.addMovieYear);
        movieGenre = (TextView) findViewById(R.id.addMovieGenre);
        movieDescription = (TextView) findViewById(R.id.addMovieDescription);
        movieCost = (TextView) findViewById(R.id.addMovieCost);
        moviePicture = (TextView) findViewById(R.id.addMoviePicture);
        movieTrailer = (TextView) findViewById(R.id.addMovieTrailer);


    }

    private void limpiar() {
        movieAmount.setText("");
        movieName.setText("");
        movieYear.setText("");
        movieGenre.setText("");
        movieDescription.setText("");
        movieCost.setText("");
        moviePicture.setText("");
        movieTrailer.setText("");

    }

    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.addMovieBack: {
                finish();
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.addMovieAddMovie: {
                try {
                    if (movieAmount.getText().toString().isEmpty() || movieName.getText().toString().isEmpty() || movieYear.getText().toString().isEmpty() || movieGenre.getText().toString().isEmpty() || movieDescription.getText().toString().isEmpty() || movieCost.getText().toString().isEmpty() || moviePicture.getText().toString().isEmpty() || movieTrailer.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Rellene los datos en blanco", Toast.LENGTH_SHORT).show();
                    } else {
                        conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                        SQLiteDatabase db = conn.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Constants.FIELD_MOVIE_AMOUNT, movieAmount.getText().toString());
                        values.put(Constants.FIELD_MOVIE_NAME, movieName.getText().toString());
                        values.put(Constants.FIELD_MOVIE_YEAR, movieYear.getText().toString());
                        values.put(Constants.FIELD_MOVIE_GENRE, movieGenre.getText().toString());
                        values.put(Constants.FIELD_MOVIE_DESCRIPTION, movieDescription.getText().toString());
                        values.put(Constants.FIELD_MOVIE_COST, movieCost.getText().toString());
                        values.put(Constants.FIELD_MOVIE_PICTURE, moviePicture.getText().toString());
                        values.put(Constants.FIELD_MOVIE_TRAILER, movieTrailer.getText().toString());
                        db.insert(Constants.TABLE_MOVIES, null, values);
                        Toast.makeText(getApplicationContext(), "Pelicula correctamente agregada", Toast.LENGTH_SHORT).show();
                        db.close();
                        conn.close();
                        limpiar();
                        recreate();
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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
}