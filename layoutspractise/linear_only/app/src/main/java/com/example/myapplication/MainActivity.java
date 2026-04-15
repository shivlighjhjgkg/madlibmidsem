package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText name, email;
    RadioButton male, female;
    Spinner course;
    Button submit;
    RadioGroup genderGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML with Java
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        course = findViewById(R.id.course);
        submit = findViewById(R.id.submit);
        genderGroup = findViewById(R.id.genderGroup);

        // Button Click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = name.getText().toString();
                String userEmail = email.getText().toString();
                String userGender = "";

                if (male.isChecked()) {
                    userGender = "Male";
                } else if (female.isChecked()) {
                    userGender = "Female";
                }

                String userCourse = course.getSelectedItem().toString();

                String message = "Name: " + userName +
                        "\nEmail: " + userEmail +
                        "\nGender: " + userGender +
                        "\nCourse: " + userCourse;

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}