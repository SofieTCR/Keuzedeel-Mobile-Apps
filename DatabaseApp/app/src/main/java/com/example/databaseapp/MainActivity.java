package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase myDB = this.openOrCreateDatabase("UsersDB", MODE_PRIVATE, null);

        File dbFile = getDatabasePath("UsersDB");
        if (dbFile.exists()) {
            Log.i("DBInfo", "DB Exists!");
        }
        else {
            Log.e("DBInfo", "DB not found!");
        }
        myDB.execSQL("CREATE TABLE IF NOT EXISTS"
                   + "       details            "
                   + "(username VARCHAR(255), password VARCHAR(255));"
                    );

        myDB.execSQL("INSERT INTO details (username, password) VALUES ('Soph', '0000');");

        Cursor c = myDB.rawQuery("SELECT rowid, * FROM details;", null);
        int colom0 = c.getColumnIndex("rowid");
        int colom1 = c.getColumnIndex("username");
        int colom2 = c.getColumnIndex("password");

        for (int row = 0; row < c.getCount(); row++)
        {
            c.moveToPosition(row);
            Log.i("DBInfo", c.getString(colom0));
            Log.i("DBInfo", c.getString(colom1));
            Log.i("DBInfo", c.getString(colom2));
        }
        myDB.close();
    }
}