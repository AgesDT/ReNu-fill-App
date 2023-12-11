package com.example.renu_fill;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "Renufill.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table accounts(accID NUMBER, email TEXT primary key, password TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists accounts");
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
    public void dropTable(String table){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("drop Table if exists "+table);
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



}