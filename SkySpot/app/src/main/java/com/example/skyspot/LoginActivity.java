package com.example.skyspot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SQLiteDatabase myDB = this.openOrCreateDatabase("UsersDB", MODE_PRIVATE, null);

        File dbFile = getDatabasePath("UsersDB");
        if (dbFile.exists()) {
            Log.i("DBInfo", "DB Exists!");
        }
        else {
            Log.e("DBInfo", "DB not found!");
        }
        myDB.execSQL("CREATE TABLE IF NOT EXISTS Users (\n" +
                "     UserId INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "     Username TEXT UNIQUE,\n" +
                "     PasswordHash TEXT\n" +
                ");"
        );

        myDB.execSQL("CREATE TABLE IF NOT EXISTS UserLocations (\n" +
                "    Indx INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    UserId INTEGER,\n" +
                "    Location TEXT,\n" +
                "    FOREIGN KEY(UserId) REFERENCES Users(UserId)\n" +
                ");"
        );
    }
}