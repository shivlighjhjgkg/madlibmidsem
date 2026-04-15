package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdditionalDetailsActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    Spinner spinnerDept;
    DatePicker datePicker;
    Button btnSaveMore, btnMoveAhead;
    long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_details);
        myDb = new DatabaseHelper(this);

        spinnerDept = findViewById(R.id.spinner_dept);
        datePicker = findViewById(R.id.date_picker);
        btnSaveMore = findViewById(R.id.btn_save_more);
        btnMoveAhead = findViewById(R.id.btn_move_ahead);

        userId = getIntent().getLongExtra("USER_ID", -1);

        btnSaveMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(AdditionalDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnMoveAhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                Intent intent = new Intent(AdditionalDetailsActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveData() {
        String dept = spinnerDept.getSelectedItem().toString();
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year = datePicker.getYear();
        String dob = day + "/" + month + "/" + year;

        boolean isUpdated = myDb.updateDetails(userId, dept, dob);
        if (isUpdated) {
            Toast.makeText(AdditionalDetailsActivity.this, "Details Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AdditionalDetailsActivity.this, "Details not Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
