package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editName, editEmail;
    RadioGroup radioGroupGender;
    CheckBox cbCoding, cbReading, cbSports;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.et_name);
        editEmail = findViewById(R.id.et_email);
        radioGroupGender = findViewById(R.id.rg_gender);
        cbCoding = findViewById(R.id.cb_coding);
        cbReading = findViewById(R.id.cb_reading);
        cbSports = findViewById(R.id.cb_sports);
        btnSubmit = findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString();
                String email = editEmail.getText().toString();
                
                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (myDb.isEmailExists(email)) {
                    Toast.makeText(MainActivity.this, "Email already exists! Please use a different one.", Toast.LENGTH_LONG).show();
                    return;
                }

                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton radioButton = findViewById(selectedId);
                String gender = radioButton.getText().toString();

                StringBuilder interests = new StringBuilder();
                if (cbCoding.isChecked()) interests.append("Coding ");
                if (cbReading.isChecked()) interests.append("Reading ");
                if (cbSports.isChecked()) interests.append("Sports ");

                long id = myDb.insertData(name, email, gender, interests.toString().trim());
                if (id != -1) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AdditionalDetailsActivity.class);
                    intent.putExtra("USER_ID", id);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
