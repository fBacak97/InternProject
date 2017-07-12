package com.example.furkanubuntu.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by furkanubuntu on 7/10/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    Context context;
    public static final String USER_TABLE = "credentials";
    public static final String WISHLIST_TABLE = "wishlist";
    public static final String CART_TABLE = "cart";
    public static final String DATABASE_NAME = "ProjectDB";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

    public void addWishlist(String link, String description, String department, int userID) {
        SQLiteDatabase db = this.getWritableDatabase();
        if (db.query("wishlist",new String[] {"link"}, "link = ? AND key = ?",new String[] {link, Integer.toString(userID)},null,null,null,null).getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("link", link);
            values.put("description", description);
            values.put("department", department);
            values.put("key", userID);
            db.insert("wishlist", null, values);
            Toast toast = Toast.makeText(context,"The item is added to your wishlist.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else{
            Toast toast = Toast.makeText(context,"The item is already in your wishlist.", Toast.LENGTH_SHORT);
            toast.show();
        }
        db.close();
    }

    public ArrayList<JsonItemOnSale> readWishlist(int userID) {
        ArrayList<JsonItemOnSale> jsonArray = new ArrayList<JsonItemOnSale>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("wishlist", new String[] {"description", "link", "department"}, "key" + " = ?", new String[] {Integer.toString(userID)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            Log.d("null", String.valueOf(cursor.getCount()));
        }
        for(int i = 0; i < cursor.getCount(); i++){
            jsonArray.add(new JsonItemOnSale("Random", "Random", cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return jsonArray;
    }

    public void removeWishlist(String link, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("wishlist", "link = ? AND key = ?", new String[] {link, Integer.toString(userID)});
        db.close();
    }

    public int checkCredentials(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.query("credentials",new String[] {"ID"},"username = ? AND password = ?",new String[] {username,password},null,null,null,null).getCount() > 0) {
            Cursor cursor = db.query("credentials",new String[] {"ID"},"username = ? AND password = ?",new String[] {username,password},null,null,null,null);
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }

    public boolean addCredentials(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.query("credentials",new String[] {"ID"},"username = ?",new String[] {username},null,null,null,null).getCount() > 0){
            return false;
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            db.insert("credentials", null, values);
            return true;
        }
    }
}
