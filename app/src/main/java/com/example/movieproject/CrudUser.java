package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieproject.adminfragments.TableUser;
import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.tables.users;

public class CrudUser extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    TextView id,name,lastname,username,password,moviesOwned,moneySpent;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_user);

        id = (TextView) findViewById(R.id.crudUserId);
        name = (TextView) findViewById(R.id.crudUser_Name);
        lastname = (TextView) findViewById(R.id.crudUserLastName);
        username = (TextView) findViewById(R.id.crudUserUsername);
        password = (TextView) findViewById(R.id.crudUserPassword);
        moviesOwned = (TextView) findViewById(R.id.crudUserMoviesOwned);
        moneySpent = (TextView) findViewById(R.id.crudUserMoneySpent);

        Bundle object = getIntent().getExtras();
        users usuario = null;

        if(object!=null){
            usuario = (users) object.getSerializable("user");
            id.setText(usuario.getUserId().toString());
            name.setText(usuario.getName().toString());
            lastname.setText(usuario.getLastName().toString());
            username.setText(usuario.getUsername().toString());
            password.setText(usuario.getPassword().toString());
            moviesOwned.setText(usuario.getMoviesOwned().toString());
            moneySpent.setText(usuario.getMoneySpent().toString());
        }
    }


    public void onClick(View view) {
        int resId = 0;
        switch (view.getId()) {

            case R.id.crudUserDelete:
            {
                try {
                    conn = new ConexionSQLiteHelper(this,"MoviesDataBase",null,1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {id.getText().toString()};

                    db.delete(Constants.TABLE_USERS,Constants.FIELD_ID+"=?", parametros);
                    Toast.makeText(getApplicationContext(), "Se elimino correctamente el usuario", Toast.LENGTH_SHORT).show();
                    db.close();
                    conn.close();
                    finish();
                }catch (Exception e){
                }
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.crudUserUpdate:{

                try {
                    conn = new ConexionSQLiteHelper(this,"MoviesDataBase",null,1);
                    SQLiteDatabase db = conn.getWritableDatabase();
                    String[] parametros = {id.getText().toString()};
                    ContentValues values = new ContentValues();
                    values.put(Constants.FIELD_ID, id.getText().toString());
                    values.put(Constants.FIELD_NAME,name.getText().toString());
                    values.put(Constants.FIELD_LASTNAME,lastname.getText().toString());
                    values.put(Constants.FIELD_USERNAME,username.getText().toString());
                    values.put(Constants.FIELD_PASSWORD,password.getText().toString());
                    values.put(Constants.FIELD_MOVIESOWNED,moviesOwned.getText().toString());
                    values.put(Constants.FIELD_MONEYSPENT,moneySpent.getText().toString());
                    db.update(Constants.TABLE_USERS,values,Constants.FIELD_ID+"=?",parametros);
                    Toast.makeText(getApplicationContext(), "Se actualizo correctamente el usario", Toast.LENGTH_SHORT).show();
                    db.close();
                    conn.close();


                    finish();
                } catch (Exception e){

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