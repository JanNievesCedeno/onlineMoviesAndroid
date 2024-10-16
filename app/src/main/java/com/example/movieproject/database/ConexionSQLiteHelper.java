package com.example.movieproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_USERS);
        db.execSQL(Constants.CREATE_TABLE_MOVIES);
        db.execSQL(Constants.CREATE_TABLE_SALES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USERS + "");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_MOVIES + "");
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_SALES + "");
        onCreate(db);
    }
}
