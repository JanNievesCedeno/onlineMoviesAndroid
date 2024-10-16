package com.example.movieproject.fragments;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.movieproject.MainActivity;
import com.example.movieproject.MovieInfo;
import com.example.movieproject.R;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;

import java.util.ArrayList;


public class movieList extends Fragment implements View.OnClickListener {


    public ArrayList<movies> listMovies;
    public RecyclerView recyclerMovies;
    private View.OnClickListener listener;
    ConexionSQLiteHelper conn;
    Integer userId;
    public AdapterMovieList adapter;
    SearchView search;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_movie_list, container, false);
        setHasOptionsMenu(true);
        listMovies = new ArrayList<>();

        recyclerMovies = (RecyclerView) vista.findViewById(R.id.movieListRecyclerView);
        recyclerMovies.setLayoutManager(new GridLayoutManager(getContext(),2));

        consultarMovies();

        adapter = new AdapterMovieList(listMovies);

        adapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                movies pelicula = listMovies.get(recyclerMovies.getChildAdapterPosition(view));
                Intent intentItemInfo = new Intent(getContext(), MovieInfo.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie",pelicula);
                intentItemInfo.putExtras(bundle);
                startActivity(intentItemInfo);
            }
        });

        recyclerMovies.setAdapter(adapter);



        return vista;
    }


    @SuppressLint("ResourceType")
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getContext()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW| MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filtrado(newText);
                return false;
            }
        });

        // this will copy menu values to upper defined menu so that we can change icon later akash
    }

    public void consultarMovies() {

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();

        movies pelicula=null;

        try {
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
        } catch (Exception e) {

        }

    }

    @Override
    public void onClick(View view) {

    }

}