package com.example.movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.utilidades.criptografia;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Register extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private static final String AES = "AES";

    EditText fieldName,fieldLastName,fieldUsername,fieldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fieldName = (EditText) findViewById(R.id.name);
        fieldLastName = (EditText) findViewById(R.id.lastName);
        fieldUsername = (EditText) findViewById(R.id.username);
        fieldPassword = (EditText) findViewById(R.id.password);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void registerNewUser() throws Exception {

        String name = fieldName.getText().toString();
        String lastname = fieldLastName.getText().toString();
        String username = fieldUsername.getText().toString();
        String password = fieldPassword.getText().toString();

        if(name.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty() ) {

            Toast.makeText(getApplicationContext(), "Rellene los datos en blanco", Toast.LENGTH_SHORT).show();

        } else {
            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"MoviesDataBase",null,1);
            SQLiteDatabase db = conn.getReadableDatabase();

            String[] parametros = {fieldUsername.getText().toString()};
            Cursor cursor = db.rawQuery("SELECT  "+Constants.FIELD_USERNAME+" FROM "+Constants.TABLE_USERS+" WHERE "+Constants.FIELD_USERNAME+"=?", parametros);

            if(cursor.moveToFirst()) {

                Toast.makeText(getApplicationContext(), "Ya existe este usuario", Toast.LENGTH_SHORT).show();

            } else {

                criptografia n = new criptografia();
                db = conn.getWritableDatabase();
                String insert =
                        "INSERT INTO "+ Constants.TABLE_USERS +" ( "+Constants.FIELD_NAME+", "+Constants.FIELD_LASTNAME+", "+Constants.FIELD_USERNAME+", "+Constants.FIELD_PASSWORD+") VALUES ('"+fieldName.getText().toString()+"', '"+fieldLastName.getText().toString()+"', '"+fieldUsername.getText().toString()+"', '"+n.encriptar(password,username)+"')";

                db.execSQL(insert);
                Toast.makeText(getApplicationContext(), "Se registro su usuario.", Toast.LENGTH_SHORT).show();
                db.close();

                finish();
                Intent intentLogin = new Intent(Register.this,Login.class);
                startActivity(intentLogin);
                overridePendingTransition(R.anim.from_left,R.anim.to_right);
            }

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View view) {
        int resId = 0;
        Intent intent;
        switch (view.getId()) {

            case R.id.back:
            {
                finish();
                Intent intentBack = new Intent(Register.this,Login.class);
                startActivity(intentBack);
                overridePendingTransition(R.anim.from_left,R.anim.to_right);
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.registerSignup:{
                try {
                    registerNewUser();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
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