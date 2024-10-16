package com.example.movieproject.adminfragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieproject.Admin;
import com.example.movieproject.CrudMovies;
import com.example.movieproject.CrudUser;
import com.example.movieproject.Login;
import com.example.movieproject.R;
import com.example.movieproject.Register;
import com.example.movieproject.addMovie;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;
import com.example.movieproject.database.tables.users;

import java.util.ArrayList;


public class TableMovies extends Fragment implements View.OnClickListener {
    private MediaPlayer mediaPlayer;
    ArrayList<movies> listMovies;
    RecyclerView recyclerMovies;
    private View.OnClickListener listener;

    Button addMovies;

    ConexionSQLiteHelper conn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_table_movies, container, false);

        addMovies = (Button) vista.findViewById(R.id.addMovies);
        addMovies.setOnClickListener(this);

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);

        listMovies = new ArrayList<>();

        recyclerMovies = (RecyclerView) vista.findViewById(R.id.tableMoviesRecyclerView);
        recyclerMovies.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        consultarMovies();

        AdapterMovies adapter = new AdapterMovies(listMovies);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                movies pelicula = listMovies.get(recyclerMovies.getChildAdapterPosition(view));

                Intent intentCrudMovies = new Intent(getContext(), CrudMovies.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie",pelicula);
                intentCrudMovies.putExtras(bundle);
                startActivity(intentCrudMovies);
            }
        });

        recyclerMovies.setAdapter(adapter);
        return vista;
    }

    private void consultarMovies() {

        SQLiteDatabase db = conn.getReadableDatabase();

        movies pelicula=null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Constants.TABLE_MOVIES, null);

        while (cursor.moveToNext()){
            pelicula = new movies();
            pelicula.setMovieId(cursor.getInt(0));
            pelicula.setMovieAmount(cursor.getInt(1));
            pelicula.setMovieName(cursor.getString(2));
            pelicula.setMovieYear(cursor.getString(3));
            pelicula.setMovieGenre(cursor.getString(4));
            pelicula.setMovieDescription(cursor.getString(5));
            pelicula.setMovieCost(cursor.getFloat(6));
            pelicula.setMoviePicture(cursor.getString(7));
            pelicula.setMovieTrailer(cursor.getString(8));

            listMovies.add(pelicula);

        }

        db.close();
        conn.close();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        int resId = 0;
        if (listener!=null) {
            listener.onClick(view);
        }

        switch (view.getId()) {
            case R.id.addMovies:

                Intent intentAddMovie = new Intent(getContext(), com.example.movieproject.addMovie.class);
                startActivity(intentAddMovie);
                resId = R.raw.popwhooshlight;
                break;
        }

        if(mediaPlayer!=null)
            mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getContext(),resId);
        mediaPlayer.start();
    }


}