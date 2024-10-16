package com.example.movieproject.adminfragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.movieproject.Admin;
import com.example.movieproject.CrudUser;
import com.example.movieproject.Login;
import com.example.movieproject.MainActivity;
import com.example.movieproject.R;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.users;

import java.util.ArrayList;


public class TableUser extends Fragment {
    ArrayList<users> listUsers;
    RecyclerView recyclerUsers;

    ConexionSQLiteHelper conn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_table_user, container, false);

        conn = new ConexionSQLiteHelper(getContext(),"MoviesDataBase",null,1);

        listUsers = new ArrayList<>();

        recyclerUsers = (RecyclerView) vista.findViewById(R.id.tableUserRecyclerView);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        consultarUsers();

        AdapterUser adapter = new AdapterUser(listUsers);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                users usuario = listUsers.get(recyclerUsers.getChildAdapterPosition(view));


                Intent intentCrudUser = new Intent(getContext(), CrudUser.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",usuario);
                intentCrudUser.putExtras(bundle);
                startActivity(intentCrudUser);
            }
        });
        recyclerUsers.setAdapter(adapter);

        return vista;
    }

    private void consultarUsers() {
        SQLiteDatabase db = conn.getReadableDatabase();

        users usuario=null;

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Constants.TABLE_USERS, null);

        while (cursor.moveToNext()){
            usuario = new users();
            usuario.setUserId(cursor.getInt(0));
            usuario.setName(cursor.getString(1));
            usuario.setLastName(cursor.getString(2));
            usuario.setUsername(cursor.getString(3));
            usuario.setPassword(cursor.getString(4));
            usuario.setMoviesOwned(cursor.getInt(5));
            usuario.setMoneySpent(cursor.getFloat(6));

            listUsers.add(usuario);

        }

        db.close();
        conn.close();
    }

}