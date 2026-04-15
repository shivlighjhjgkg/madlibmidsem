package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Registration.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "GENDER";
    public static final String COL_5 = "INTERESTS";
    public static final String COL_6 = "DEPARTMENT";
    public static final String COL_7 = "DOB";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT UNIQUE,GENDER TEXT,INTERESTS TEXT,DEPARTMENT TEXT,DOB TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String name, String email, String gender, String interests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, gender);
        contentValues.put(COL_5, interests);
        return db.insert(TABLE_NAME, null, contentValues);
    }

    public boolean updateDetails(long id, String department, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_6, department);
        contentValues.put(COL_7, dob);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateAllData(String id, String name, String email, String gender, String interests, String department, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, gender);
        contentValues.put(COL_5, interests);
        contentValues.put(COL_6, department);
        contentValues.put(COL_7, dob);
        int result = db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return result > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public Cursor getUser(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("select * from " + TABLE_NAME + " where ID = ?", new String[]{id});
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + "=?", new String[]{email});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public boolean isEmailExistsForOther(String id, String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_3 + "=? AND " + COL_1 + "!=?", new String[]{email, id});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}
