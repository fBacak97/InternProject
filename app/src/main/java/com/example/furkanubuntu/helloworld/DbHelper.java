package com.example.furkanubuntu.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by furkanubuntu on 7/10/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "credentials";
    public static final String WISHLIST_TABLE = "wishlist";
    public static final String CART_TABLE = "cart";
    public static final String DATABASE_NAME = "ProjectDB";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1,username TEXT,password TEXT);");
        ContentValues content = new ContentValues();
        content.put("username", "furkan");
        content.put("password", "hello");
        long newRowID = sqLiteDatabase.insert("credentials", null, content);
        String str = String.valueOf(newRowID);
        Log.d("ROW", str);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + WISHLIST_TABLE + "(link TEXT,description TEXT,department TEXT, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
        ContentValues content2 = new ContentValues();
        content2.put("link","https://www.w3schools.com/css/trolltunga.jpg");
        content2.put("description","HelloWorld");
        content2.put("department","books+audiobooks");
        content2.put("key" , 1);
        sqLiteDatabase.insert("wishlist",null,content2);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CART_TABLE + "(link TEXT,description TEXT,department TEXT, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        Log.d("Table","Tables got deleted.");
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addWishlist(String link, String description, String department) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("link", link);
        values.put("description", description);
        values.put("department", department);
        values.put("key", 1);
        db.insert("wishlist", null, values);
        db.close();
    }

    public JsonItemOnSale readWishlist(int position) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("wishlist", new String[] {"description", "link", "department"}, "key" + "=?", new String[] { "1" }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d("null", String.valueOf(cursor.getCount()));
        }
        for(int i = 0; i < position; i++){
            cursor.moveToNext();
        }

        JsonItemOnSale item = new JsonItemOnSale("Random", "Random", cursor.getString(0), cursor.getString(1), cursor.getString(2));
        cursor.close();
        return item;
    }
}
