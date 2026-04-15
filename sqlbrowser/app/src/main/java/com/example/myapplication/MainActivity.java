package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Manual inset handling for version 1.7.2 compatibility
        if (findViewById(R.id.main) != null) {
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        dataTextView = findViewById(R.id.dataTextView);
        dbHelper = new DatabaseHelper(this);

        displayData();
    }

    private void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        StringBuilder builder = new StringBuilder();
        builder.append("Database Content:\n\n");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));

            builder.append("ID: ").append(id)
                    .append("\nName: ").append(name)
                    .append("\nEmail: ").append(email)
                    .append("\n\n");
        }
        cursor.close();

        if (builder.length() <= 19) { // Only the header "Database Content:\n\n"
            dataTextView.setText("Database is empty or could not be loaded.");
        } else {
            dataTextView.setText(builder.toString());
        }
    }
}