package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "example.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        copyDatabaseFromAssets();
    }

    private void copyDatabaseFromAssets() {
        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        // If you want to force overwrite with the assets version, remove the !exists() check
        // For this exercise, we'll assume the user wants to "upload" a new version to assets
        // and have the app use it.
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);
            OutputStream outputStream = new FileOutputStream(dbPath);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.d(TAG, "Database copied from assets.");
        } catch (IOException e) {
            Log.d(TAG, "No database found in assets, using default/existing one. " + e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        // Initial data if not copied from assets
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, email) VALUES ('John Doe', 'john@example.com');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (name, email) VALUES ('Jane Smith', 'jane@example.com');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}