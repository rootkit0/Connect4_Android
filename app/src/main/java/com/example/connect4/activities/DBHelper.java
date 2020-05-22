package com.example.connect4.activities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursor, int version) {
        super(context, name, cursor, version);
    }

    private String sqlCreate =
            "CREATE TABLE Matches" +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nickname VARCHAR," +
            "date VARCHAR," +
            "board_size INT," +
            "timer_status BOOLEAN," +
            "timer INT," +
            "result VARCHAR)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Matches");
        db.execSQL(sqlCreate);
    }
}
