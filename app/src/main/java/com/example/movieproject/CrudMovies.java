package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;
import com.example.movieproject.database.tables.users;

public class CrudMovies extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    TextView dataId, dataAmount, dataName, dataYear, dataGenre, dataDescription, dataCost, dataPicture, dataTrailer;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_movies);

        dataId = (TextView) findViewById(R.id.crudMovieId);
        dataAmount = (TextView) findViewById(R.id.crudMovieAmount);
        dataName = (TextView) findViewById(R.id.crudMovieName);
        dataYear = (TextView) findViewById(R.id.crudMovieYear);
        dataGenre = (TextView) findViewById(R.id.crudMovieGenre);
        dataDescription = (TextView) findViewById(R.id.crudMovieDescription);
        dataCost = (TextView) findViewById(R.id.crudMovieCost);
        dataPicture = (TextView) findViewById(R.id.crudMoviePicture);
        dataTrailer = (TextView) findViewById(R.id.crudMovieTrailer);

        Bundle object = getIntent().getExtras();
        movies pelicula = null;

        if (object != null) {
            pelicula = (movies) object.getSerializable("movie");
            dataId.setText(pelicula.getMovieId().toString());
            dataAmount.setText(pelicula.getMovieAmount().toString());
            dataName.setText(pelicula.getMovieName().toString());
            dataYear.setText(pelicula.getMovieYear().toString());
            dataGenre.setText(pelicula.getMovieGenre().toString());
            dataDescription.setText(pelicula.getMovieDescription().toString());
            dataCost.setText(pelicula.getMovieCost().toString());
            dataPicture.setText(pelicula.getMoviePicture().toString());
            dataTrailer.setText(pelicula.getMovieTrailer().toString());
        }

    }


    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.crudMoviesDelete: {
                try {
                    conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {dataId.getText().toString()};
                    db.delete(Constants.TABLE_MOVIES, Constants.FIELD_MOVIE_ID + "=?", parametros);
                    Toast.makeText(getApplicationContext(), "Se elimino correctamente la pelicula", Toast.LENGTH_SHORT).show();
                    db.close();
                    conn.close();
                    finish();
                } catch (Exception e) {
                }
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.crudMoviesUpdate: {

                try {
                    conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {dataId.getText().toString()};
                    ContentValues values = new ContentValues();
                    values.put(Constants.FIELD_MOVIE_ID, dataId.getText().toString());
                    values.put(Constants.FIELD_MOVIE_AMOUNT, dataAmount.getText().toString());
                    values.put(Constants.FIELD_MOVIE_NAME, dataName.getText().toString());
                    values.put(Constants.FIELD_MOVIE_YEAR, dataYear.getText().toString());
                    values.put(Constants.FIELD_MOVIE_GENRE, dataGenre.getText().toString());
                    values.put(Constants.FIELD_MOVIE_DESCRIPTION, dataDescription.getText().toString());
                    values.put(Constants.FIELD_MOVIE_COST, dataCost.getText().toString());
                    values.put(Constants.FIELD_MOVIE_PICTURE, dataPicture.getText().toString());
                    values.put(Constants.FIELD_MOVIE_TRAILER, dataTrailer.getText().toString());
                    db.update(Constants.TABLE_MOVIES, values, Constants.FIELD_MOVIE_ID + "=?", parametros);
                    Toast.makeText(getApplicationContext(), "Se actualizo los datos de la pelicula", Toast.LENGTH_SHORT).show();
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
}