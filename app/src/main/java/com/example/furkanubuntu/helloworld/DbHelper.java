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
    static final String SEARCH_TABLE = "searches";
    static final String SEARCH_COUNT_TABLE = "searchcounts";
    static final String CLICK_COUNT_TABLE = "clickcounts";
    static final String USER_TABLE = "credentials";
    static final String WISHLIST_TABLE = "wishlist";
    static final String CART_TABLE = "cart";
    static final String DATABASE_NAME = "ProjectDB.db";
    static final int DATABASE_VERSION = 1;
    static final String[] departmentArray = {"booksaudiobooks", "moviestv", "music", "videogames", "computersoffice", "electronics", "garden", "grocery", "beauty", "kids", "clothing", "sportsoutdoors"};

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + USER_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT DEFAULT 1,username TEXT,password TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + WISHLIST_TABLE + "(link TEXT,description TEXT,department TEXT, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CART_TABLE + "(link TEXT,description TEXT,department TEXT, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + SEARCH_COUNT_TABLE + "(booksaudiobooks INTEGER,moviestv INTEGER,music INTEGER,videogames INTEGER,computersoffice INTEGER," +
                "electronics INTEGER,garden INTEGER,grocery INTEGER,beauty INTEGER,kids INTEGER,clothing INTEGER,sportsoutdoors INTEGER, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + SEARCH_TABLE + "(search TEXT,department TEXT, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + CLICK_COUNT_TABLE + "(booksaudiobooks INTEGER,moviestv INTEGER,music INTEGER,videogames INTEGER,computersoffice INTEGER," +
                "electronics INTEGER,garden INTEGER,grocery INTEGER,beauty INTEGER,kids INTEGER,clothing INTEGER,sportsoutdoors INTEGER, key INTEGER, FOREIGN KEY(key) REFERENCES credentials(ID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { ///////////////REMOVİNG AND CREATİNG ALL OF THEM MİGHT BE THE REASON OF THE CRASHES!!!
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WISHLIST_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CART_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SEARCH_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SEARCH_COUNT_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CLICK_COUNT_TABLE);
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
            db.close();
            return cursor.getInt(0);
        }
        db.close();
        return 0;
    }

    public boolean addCredentials(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.query("credentials",new String[] {"ID"},"username = ?",new String[] {username},null,null,null,null).getCount() > 0){
            db.close();
            return false;
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put("username", username);
            values.put("password", password);
            db.insert("credentials", null, values);
            Cursor cursor = db.query("credentials",new String[] {"ID"},"username = ?",new String[] {username},null,null,null,null);
            cursor.moveToFirst();
            int userID = cursor.getInt(0);
            ContentValues values1 = new ContentValues();
            for(int i = 0; i < departmentArray.length; i++)
                values1.put(departmentArray[i],0);
            values1.put("key", userID);
            db.insert("searchcounts",null,values1);
            db.insert("clickcounts",null,values1);
            db.close();
            return true;
        }
    }

    public void addSearch(String search, String department, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        if(db.query("searches",new String[] {"search"},"search = ? AND key = ?",new String[] {search,String.valueOf(userID)},null,null,null,null).getCount() == 0){
            ContentValues values = new ContentValues();
            values.put("search",search);
            values.put("department",department);
            values.put("key",userID);
            db.insert("searches",null,values);
            db.close();
        }
    }

    public ArrayList<String> readSearches(String department, int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        if(db.query("searches",new String[] {"search"},"department = ? AND key = ?",new String[] {department, String.valueOf(userID)},null,null,null,null).getCount() > 0) {
            ArrayList<String> searchArray = new ArrayList<>();
            Cursor cursor = db.query("searches",new String[] {"search"},"department = ? AND key = ?",new String[] {department, String.valueOf(userID)},null,null,null,null);
            cursor.moveToFirst();
            db.close();
            do{
                searchArray.add(cursor.getString(0));
            } while(cursor.moveToNext());
            return searchArray;
        }
        db.close();
        return null;
    }

    public int readSearchCountOneValue(String department, int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("searchcounts",new String[] {department},"key = ?",new String[] {String.valueOf(userID)},null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getInt(0);
    }

    public String readMaxSearchCount(int userID){
        int max = 0;
        for(int i = 0; i < departmentArray.length; i++){
            int value = readSearchCountOneValue(departmentArray[i], userID);
            if(value > max)
                max = i;
        }
        return departmentArray[max];
    }

    public void addSearchCount(String department, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("searchcounts",new String[] {department},"key = ?",new String[] {String.valueOf(userID)},null,null,null,null);
        cursor.moveToFirst();
        int oldValue = cursor.getInt(0);
        ContentValues values = new ContentValues();
        values.put(department,String.valueOf(oldValue + 1));
        db.update("searchcounts",values,"key = ?",new String[] {String.valueOf(userID)});
        db.close();
    }

    public int readClickCountOneValue(String department, int userID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("clickcounts",new String[] {department},"key = ?",new String[] {String.valueOf(userID)},null,null,null,null);
        cursor.moveToFirst();
        db.close();
        return cursor.getInt(0);
    }

    public String readMaxClickCount(int userID){
        int max = 0;
        for(int i = 0; i < departmentArray.length; i++){
            int value = readClickCountOneValue(departmentArray[i], userID);
            if(value > max)
                max = i;
        }
        return departmentArray[max];
    }

    public void addClickCount(String department, int userID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("clickcounts",new String[] {department},"key = ?",new String[] {String.valueOf(userID)},null,null,null,null);
        cursor.moveToFirst();
        int oldValue = cursor.getInt(0);
        ContentValues values = new ContentValues();
        values.put(department,String.valueOf(oldValue + 1));
        db.update("clickcounts",values,"key = ?",new String[] {String.valueOf(userID)});
        db.close();
    }
}
