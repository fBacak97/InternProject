package com.example.furkanubuntu.helloworld;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by furkanubuntu on 7/10/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProjectDB";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS credentials(ID PRIMARY KEY,username VARCHAR,password VARCHAR, wishlistNo INTEGER UNIQUE, cartNo INTEGER UNIQUE);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS wishlist(link VARCHAR,description VARCHAR,department VARCHAR, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(wishlistNo);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS cart(link VARCHAR,description VARCHAR,department VARCHAR, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(cartNo);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
