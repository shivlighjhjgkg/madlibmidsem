package com.example.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 Setup Toolbar to show Menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textContent = findViewById(R.id.textContent);
    }

    // 🔹 Inflate Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // 🔹 Handle Clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        // TEXT MENU
        if (id == R.id.menu_workout) {
            textContent.setText("Workout Plans:\n- Weight Loss\n- Cardio\n- Strength Training");
        }
        else if (id == R.id.menu_trainers) {
            textContent.setText("Trainers:\n1. John - Cardio Expert\n2. Sara - Yoga Trainer");
        }
        else if (id == R.id.menu_membership) {
            textContent.setText("Membership:\nBasic - ₹1000\nPremium - ₹2500");
        }

        // ICON MENU
        else if (id == R.id.menu_home) {
            textContent.setText("Welcome to XYZ Fitness Center");
        }
        else if (id == R.id.menu_about) {
            textContent.setText("About Us:\nWe provide best fitness training.");
        }
        else if (id == R.id.menu_contact) {
            textContent.setText("Contact Us:\nPhone: 9876543210");
        }

        return true;
    }
}