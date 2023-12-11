package com.example.renu_fill;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Renufill.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table accounts(accID NUMBER, email TEXT primary key, password TEXT)");
        DB.execSQL("create Table purchases(barcode TEXT primary key, accID NUMBER, purID NUMBER, capacity NUMBER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists accounts");
        DB.execSQL("drop Table if exists purchases");
    }
    public Boolean insertuserdata(int accID, String email, String password)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("accID", accID);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result=DB.insert("accounts", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public Boolean insertpurchase(String barcode, int purID, int accID, int capacity)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("barcode", barcode);
        contentValues.put("purID", purID);
        contentValues.put("capacity", capacity);
        contentValues.put("accID", accID);

        long result=DB.insert("purchases", null, contentValues);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }


    public Cursor getData(String table)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from "+table, null);
        return cursor;
    }

    public boolean isUserRegistered(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from accounts where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    @SuppressLint("Range")
    public String findAccID(String email) {
        SQLiteDatabase DB = this.getWritableDatabase();
        String accID = null;

        try {
            Cursor cursor = DB.rawQuery("SELECT accID FROM accounts WHERE email = ?", new String[]{email});

            // Check if the cursor is not null and it has at least one row
            if (cursor != null && cursor.moveToFirst()) {
                // Retrieve the value of the accID column
                accID = cursor.getString(cursor.getColumnIndex("accID"));
            }

            // Close the cursor to avoid resource leaks
            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            // Handle any potential exceptions, log or display a message
            e.printStackTrace();
        } finally {
            // Close the database to avoid potential resource leaks
            if (DB != null && DB.isOpen()) {
                DB.close();
            }
        }

        return accID;
    }


//    public ArrayList<currentAcc> storeDataLocal{
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = getData("accounts");
//        ArrayList<currentAcc> arrayList = new ArrayList<>();
//
//    }
}