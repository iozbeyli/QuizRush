package com.fromthemind.quizrush.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fromthemind.quizrush.R;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by Melih on 18.05.2017.
 */

public class RushDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "rush"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database

    public RushDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            Log.d("SQL","giriyor");
            //db.execSQL("DROP TABLE FLAG");
            db.execSQL("CREATE TABLE FLAG (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "FLAG_NUMBER INTEGER, "
                    + "NAME TEXT, "
                    + "IMAGE BLOB);");
        }
        if (oldVersion < 2) {
          //  db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    public static void insertFlag(SQLiteDatabase db, int flagNumber, String flagName, byte[] image){
        ContentValues flagValues = new ContentValues();
        flagValues.put("FLAG_NUMBER",flagNumber);
        flagValues.put("NAME",flagName);
        flagValues.put("IMAGE",image);
        db.insert("FLAG", null, flagValues);
    }


    public static byte[] retrieveFlag(SQLiteDatabase db, String flagName){
        Log.d("Demanded",flagName);
        byte[] imageArray=null;
        Cursor cursor = db.query("FLAG",new String[]{"NAME", "FLAG_NUMBER", "IMAGE"},"NAME = ?",new String[]{flagName},null,null,null);
        if (cursor.moveToFirst()) {
            //Get the drink details from the cursor
            while (!cursor.isAfterLast()) {
                Log.d("Found flag",cursor.getString(0));
                imageArray = cursor.getBlob(2);
                cursor.moveToNext();
            }
        }
        if(cursor!=null && !cursor.isClosed()){
            cursor.close();
        }
        db.close();
        return imageArray;
    }

    public static ArrayList<Integer> retrieveOfflineFlags(SQLiteDatabase db){
        ArrayList<Integer> list=new ArrayList<Integer>();
        Cursor cursor = db.query("FLAG",new String[]{"FLAG_NUMBER","NAME"},null,null,null,null,null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int flag_number = cursor.getInt(0);
                if(flag_number>0){
                    list.add(flag_number);
                }
                Log.d("Helper flag: ",""+cursor.getString(1));
                cursor.moveToNext();
            }
        }
        if(cursor!=null && !cursor.isClosed()){
            cursor.close();
        }
        db.close();
        return list;


    }


}
