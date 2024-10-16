package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieInfo extends AppCompatActivity {
//    private MediaPlayer mediaPlayer;
    Integer userId, movieId;


    WebView infoVideoPlayer;
    ImageView infoImage;
    TextView infoName, infoDescription, infoYear, infoGenre, infoCost;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        infoVideoPlayer = (WebView) findViewById(R.id.infoVideoPlayer);
        infoImage = (ImageView) findViewById(R.id.infoImage);
        infoName = (TextView) findViewById(R.id.infoName);
        infoDescription = (TextView) findViewById(R.id.infoDescription);
        infoYear = (TextView) findViewById(R.id.infoYear);
        infoGenre = (TextView) findViewById(R.id.infoGenre);
        infoCost = (TextView) findViewById(R.id.infoCost);


        Bundle object = getIntent().getExtras();
        movies pelicula = null;

        if (object != null) {
            pelicula = (movies) object.getSerializable("movie");

            String video = "<html><body style='margin:0;padding:0;'><iframe width=\"100%\" height=\"100%\" " +
                    "src=\"https://www.youtube.com/embed/" + pelicula.getMovieTrailer() + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";

            movieId = pelicula.getMovieId();
            infoVideoPlayer.loadData(video, "text/html", "utf-8");
            infoVideoPlayer.getSettings().setJavaScriptEnabled(true);
//            infoVideoPlayer.getSettings().setAllowContentAccess(true);
            infoVideoPlayer.getSettings().setDomStorageEnabled(true);
//            infoVideoPlayer.getSettings().setLoadWithOverviewMode(true);
//            infoVideoPlayer.getSettings().setUseWideViewPort(true);
            infoVideoPlayer.setWebChromeClient(new WebChromeClient() {

                private View customView;
                private WebChromeClient.CustomViewCallback customViewCallback;

                @Override
                public void onShowCustomView(View view, CustomViewCallback callback) {
                    // Enter Fullscreen mode
                    if (customView != null) {
                        onHideCustomView();
                        return;
                    }

                    customView = view;
                    customViewCallback = callback;

                    // Hide the WebView and show the fullscreen video
                    infoVideoPlayer.setVisibility(View.GONE);
                    // Add customView to the layout (fullscreen video)
                    FrameLayout fullScreenContainer = findViewById(R.id.fullScreenContainer);
                    fullScreenContainer.addView(customView, new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.MATCH_PARENT
                    ));
                    fullScreenContainer.setVisibility(View.VISIBLE);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                }

                @Override
                public void onHideCustomView() {
                    // Exit Fullscreen mode
                    if (customView == null) {
                        return;
                    }

                    // Remove fullscreen video view and show original WebView again
                    FrameLayout fullScreenContainer = findViewById(R.id.fullScreenContainer);
                    fullScreenContainer.removeView(customView);
                    fullScreenContainer.setVisibility(View.GONE);

                    infoVideoPlayer.setVisibility(View.VISIBLE);

                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    customViewCallback.onCustomViewHidden();
                    customView = null;

                    // Optionally reset menu visibility or activity state
                    // Make sure the menu (and other views) are restored correctly.
                }
            });



            Glide.with(this).load(pelicula.getMoviePicture()).into(infoImage);
            infoName.setText(pelicula.getMovieName().toString());
            infoDescription.setText(pelicula.getMovieDescription().toString());
            infoYear.setText(pelicula.getMovieYear().toString());
            infoGenre.setText(pelicula.getMovieGenre().toString());
            infoCost.setText(pelicula.getMovieCost().toString());

        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Handle orientation change without reloading the WebView
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Handle landscape orientation
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Handle portrait orientation
        }
    }


    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.infoBack: {
                finish();
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.infoBuy: {

                try {
                    // Consulta a la base de datos en la tabla sales para verificar si el usuario ya tiene esa pelicula
                    conn = new ConexionSQLiteHelper(this, "MoviesDataBase", null, 1);
                    SQLiteDatabase db = conn.getReadableDatabase();
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                    userId = sharedPreferences.getInt("userId", 0);
                    String[] parametros = {userId.toString(), movieId.toString()};
                    String[] campos = {Constants.FIELD_SALES_USERID, Constants.FIELD_SALES_MOVIEID};
                    Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_SALES + " WHERE " + Constants.FIELD_SALES_USERID + " = ? AND " + Constants.FIELD_SALES_MOVIEID + "= ?", parametros);

                    if (cursor.moveToFirst()) {

                        Toast.makeText(getApplication(), "Ya tiene esta pelicula", Toast.LENGTH_SHORT).show();
                    } else {
                        db.close();
                        conn.close();

                        Bundle object = getIntent().getExtras();
                        movies pelicula = null;
                        if (object != null) {
                            Intent intentTransaction = new Intent(MovieInfo.this, MovieBuyTransaction.class);
                            pelicula = (movies) object.getSerializable("movie");
                            Integer movieAmount = pelicula.getMovieAmount();
                            if (movieAmount <= 0) {
                                Toast.makeText(this, "No hay inventario", Toast.LENGTH_SHORT).show();
                            } else {
                                object.putSerializable("movie", pelicula);
                                intentTransaction.putExtras(object);
                                startActivity(intentTransaction);
                            }

                        }
                    }
                } catch (Exception e) {

                }

                resId = R.raw.heavystomp;
                break;
            }

        }

    }
}



