package com.example.movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movieproject.database.ConexionSQLiteHelper;
import com.example.movieproject.database.Constants;
import com.example.movieproject.database.utilidades.criptografia;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Login extends AppCompatActivity {
    private static final String AES = "AES";
    private MediaPlayer mediaPlayer;

    EditText fieldUsername,fieldPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldUsername = (EditText) findViewById(R.id.username);
        fieldPassword = (EditText) findViewById(R.id.password);


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void verify() {

        if (fieldUsername.getText().toString().isEmpty() || fieldPassword.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Rellene los datos en blanco", Toast.LENGTH_SHORT).show();
        } else {

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"MoviesDataBase",null,1);

            SQLiteDatabase db = conn.getReadableDatabase();

            String[] parametros = {fieldUsername.getText().toString()};

            try {
                Cursor cursor = db.rawQuery("SELECT "+Constants.FIELD_ID+","+Constants.FIELD_USERNAME+","+Constants.FIELD_PASSWORD+" FROM "+Constants.TABLE_USERS+" WHERE "+Constants.FIELD_USERNAME+"=?", parametros);

                if(cursor.moveToFirst()){

                    if (cursor.getString(1).equals("admin")){

                        String password = cursor.getString(2);
                        String username = fieldUsername.getText().toString();

                        criptografia n = new criptografia();
                        String desencriptada = n.desEncriptar(password,username);
                        String userInput = fieldPassword.getText().toString();

                        if (desencriptada.equals(userInput)) {
                            finish();
                            Intent intentLogin = new Intent(Login.this,Admin.class);
                            startActivity(intentLogin);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            Toast.makeText(getApplicationContext(), "Bienvenido Admin.", Toast.LENGTH_SHORT).show();
                        }  else {
                        cursor.close();
                        db.close();
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                    }


                    } else {
                        String password = cursor.getString(2);
                        String username = fieldUsername.getText().toString();

                        criptografia n = new criptografia();
                        String desencriptada = n.desEncriptar(password,username);
                        String userInput = fieldPassword.getText().toString();

                        if (desencriptada.equals(userInput)) {

                            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            Integer user = cursor.getInt(0);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putInt("userId",user);
                            Log.d("TagLogin","User id: "+user.toString());
                            editor.commit();
                            Intent intentLogin = new Intent(Login.this,MainActivity.class);
                            startActivity(intentLogin);
                            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                            finish();
                            Toast.makeText(getApplicationContext(), "Bienvenido "+username, Toast.LENGTH_SHORT).show();
                        } else {
                            cursor.close();
                            db.close();
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                        }
                    }



                } else {

                    cursor.close();
                    db.close();
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                }



            } catch (Exception e) {


            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onClick(View view) {
        int resId = 0;

        switch (view.getId()) {

            case R.id.login:
            {
                try {
                    verify();
                } catch (Exception e) {
                }
                resId = R.raw.popwhooshlight;
                break;
            }

            case R.id.signup:{

                Intent intentRegister = new Intent(Login.this,Register.class);
                startActivity(intentRegister);
                overridePendingTransition(R.anim.from_right,R.anim.to_left);
                finish();
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