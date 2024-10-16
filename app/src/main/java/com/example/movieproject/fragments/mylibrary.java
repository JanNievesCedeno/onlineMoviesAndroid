package com.example.movieproject.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

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
import com.example.movieproject.R;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.movies;

import java.util.ArrayList;

public class mylibrary extends Fragment implements View.OnClickListener{

    ArrayList<movies> listMovies;
    RecyclerView recyclerMovies;
    ConexionSQLiteHelper conn;
    Integer userId;
    SearchView search;
    AdapterMovieList adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mylibrary, container, false);
        setHasOptionsMenu(true);
        listMovies = new ArrayList<>();
        recyclerMovies = (RecyclerView) view.findViewById(R.id.myLibraryRecyclerView);
        recyclerMovies.setLayoutManager(new GridLayoutManager(getContext(),2));

        consultarMovies();

        adapter = new AdapterMovieList(listMovies);
        recyclerMovies.setAdapter(adapter);

        return view;
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

    private void consultarMovies() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {userId.toString()};
        Integer p;
        String[] movie;
        movies pelicula = null;

        try {
            Cursor cursor = db.rawQuery("SELECT "+ Constants.FIELD_SALES_MOVIEID +" FROM "+Constants.TABLE_SALES+" WHERE "+Constants.FIELD_SALES_USERID+"=?",parametros);
            while (cursor.moveToNext()) {
                p = cursor.getInt(0);
                Cursor d = db.rawQuery("SELECT * FROM "+ Constants.TABLE_MOVIES+" WHERE "+Constants.FIELD_MOVIE_ID+"=?", new String[]{p.toString()});

                while (d.moveToNext()){
                    pelicula = new movies();
                    pelicula.setMovieId(d.getInt(0));
                    pelicula.setMovieAmount(d.getInt(1));
                    pelicula.setMovieName(d.getString(2));
                    pelicula.setMovieYear(d.getString(3));
                    pelicula.setMovieGenre(d.getString(4));
                    pelicula.setMovieDescription(d.getString(5));
                    pelicula.setMovieCost(d.getFloat(6));
                    pelicula.setMoviePicture(d.getString(7));
                    pelicula.setMovieTrailer(d.getString(8));

                    listMovies.add(pelicula);

                }

            }
        } catch (Exception e) {

        }

        db.close();
        conn.close();

    }

    @Override
    public void onClick(View v) {

    }
}