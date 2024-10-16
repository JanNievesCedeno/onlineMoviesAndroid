package com.example.movieproject.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.movieproject.MainActivity;
import com.example.movieproject.R;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;


public class account extends Fragment {
    Integer userId;
    TextView account_User_Name,account_User_LastName,account_User_Username,account_Movies_Owned,account_Money_Spent;
    ConexionSQLiteHelper conn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);
        account_User_Name = (TextView) view.findViewById(R.id.account_User_Name);
        account_User_LastName = (TextView) view.findViewById(R.id.account_User_LastName);
        account_User_Username = (TextView) view.findViewById(R.id.account_User_Username);
        account_Movies_Owned = (TextView) view.findViewById(R.id.account_Movies_Owned);
        account_Money_Spent = (TextView) view.findViewById(R.id.account_Money_Spent);


        consulta();


        return view;
    }

    @SuppressLint("ResourceType")
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search).setVisible(false);


        // this will copy menu values to upper defined menu so that we can change icon later akash
    }

    private void consulta() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId",0);
        Log.d("Tag","FragmentAccount userId: "+userId);

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {userId.toString()};
        String[] campos = {Constants.FIELD_NAME,Constants.FIELD_LASTNAME,Constants.FIELD_USERNAME,Constants.FIELD_MOVIESOWNED,Constants.FIELD_MONEYSPENT};


        try {
            Cursor cursor = db.rawQuery("SELECT "+Constants.FIELD_NAME+","+Constants.FIELD_LASTNAME+","+Constants.FIELD_USERNAME+", "+Constants.FIELD_MOVIESOWNED+", "+Constants.FIELD_MONEYSPENT+" FROM "+Constants.TABLE_USERS+" WHERE "+Constants.FIELD_ID+"=?",parametros);
            if (cursor.moveToFirst()){
                account_User_Name.setText(cursor.getString(0));
                account_User_LastName.setText(cursor.getString(1));
                account_User_Username.setText(cursor.getString(2));
                account_Movies_Owned.setText(cursor.getString(3));
                account_Money_Spent.setText("$ "+cursor.getString(4));
            }
        } catch (Exception e) {

        }

        db.close();
        conn.close();

    }
}