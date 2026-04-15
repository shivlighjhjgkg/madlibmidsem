package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SummaryActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView tvSummary;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        myDb = new DatabaseHelper(this);
        tvSummary = findViewById(R.id.tv_summary);
        btnDelete = findViewById(R.id.btn_delete_user);

        displaySummary();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
    }

    private void displaySummary() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            tvSummary.setText("No data found");
            return;
        }

        StringBuilder builder = new StringBuilder();
        while (res.moveToNext()) {
            builder.append("ID: ").append(res.getString(0)).append("\n");
            builder.append("Name: ").append(res.getString(1)).append("\n");
            builder.append("Email: ").append(res.getString(2)).append("\n");
            builder.append("Gender: ").append(res.getString(3)).append("\n");
            builder.append("Interests: ").append(res.getString(4)).append("\n");
            builder.append("Department: ").append(res.getString(5)).append("\n");
            builder.append("DOB: ").append(res.getString(6)).append("\n");
            builder.append("----------------------------------\n\n");
        }
        tvSummary.setText(builder.toString());
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User");
        builder.setMessage("Enter the ID of the user you want to delete:");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idToDelete = input.getText().toString();
                if (idToDelete.isEmpty()) {
                    Toast.makeText(SummaryActivity.this, "Please enter an ID", Toast.LENGTH_SHORT).show();
                } else {
                    Integer deletedRows = myDb.deleteData(idToDelete);
                    if (deletedRows > 0) {
                        Toast.makeText(SummaryActivity.this, "User Deleted Successfully", Toast.LENGTH_SHORT).show();
                        displaySummary(); // Refresh summary after deletion
                    } else {
                        Toast.makeText(SummaryActivity.this, "User ID not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
