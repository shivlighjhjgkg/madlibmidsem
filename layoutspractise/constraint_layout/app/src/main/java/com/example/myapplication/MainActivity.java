package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button editBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBtn = findViewById(R.id.editBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        editBtn.setOnClickListener(v ->
                Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show());

        logoutBtn.setOnClickListener(v ->
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show());
    }
}