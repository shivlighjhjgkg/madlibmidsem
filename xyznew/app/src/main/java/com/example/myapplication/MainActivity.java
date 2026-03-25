package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    // Option Menu Button
    private ImageButton btnMenu;

    // Icon Buttons (Bottom Nav Icons)
    private ImageButton btnHome, btnAbout, btnContact;

    // Content Sections
    private LinearLayout sectionHome, sectionAbout, sectionContact;
    private LinearLayout sectionWorkout, sectionTrainers, sectionMembership;

    // Section Labels
    private TextView tvSectionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Option Menu Button
        btnMenu = findViewById(R.id.btn_menu);

        // Initialize Icon Buttons
        btnHome    = findViewById(R.id.btn_icon_home);
        btnAbout   = findViewById(R.id.btn_icon_about);
        btnContact = findViewById(R.id.btn_icon_contact);

        // Initialize Content Sections
        sectionHome       = findViewById(R.id.section_home);
        sectionAbout      = findViewById(R.id.section_about);
        sectionContact    = findViewById(R.id.section_contact);
        sectionWorkout    = findViewById(R.id.section_workout);
        sectionTrainers   = findViewById(R.id.section_trainers);
        sectionMembership = findViewById(R.id.section_membership);

        // Show Home by default
        showSection("home");

        // ── Option Menu (Workout Plans / Trainers / Membership) ──────────────
        btnMenu.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, v);
            popup.getMenuInflater().inflate(R.menu.fitness_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_workout) {
                    showSection("workout");
                    return true;
                } else if (id == R.id.menu_trainers) {
                    showSection("trainers");
                    return true;
                } else if (id == R.id.menu_membership) {
                    showSection("membership");
                    return true;
                }
                return false;
            });
            popup.show();
        });

        // ── Icon Navigation Buttons ──────────────────────────────────────────
        btnHome.setOnClickListener(v -> showSection("home"));
        btnAbout.setOnClickListener(v -> showSection("about"));
        btnContact.setOnClickListener(v -> showSection("contact"));
    }

    /** Hide all sections, then reveal the requested one. */
    private void showSection(String which) {
        sectionHome.setVisibility(View.GONE);
        sectionAbout.setVisibility(View.GONE);
        sectionContact.setVisibility(View.GONE);
        sectionWorkout.setVisibility(View.GONE);
        sectionTrainers.setVisibility(View.GONE);
        sectionMembership.setVisibility(View.GONE);

        switch (which) {
            case "home":       sectionHome.setVisibility(View.VISIBLE);       break;
            case "about":      sectionAbout.setVisibility(View.VISIBLE);      break;
            case "contact":    sectionContact.setVisibility(View.VISIBLE);    break;
            case "workout":    sectionWorkout.setVisibility(View.VISIBLE);    break;
            case "trainers":   sectionTrainers.setVisibility(View.VISIBLE);   break;
            case "membership": sectionMembership.setVisibility(View.VISIBLE); break;
        }
    }
}