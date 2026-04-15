package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String NAME_KEY = "name";
    private static final String AGE_KEY = "age";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME_KEY, editTextName.getText().toString());
        editor.putString(AGE_KEY, editTextAge.getText().toString());

        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME_KEY, "");
        String age = sharedPreferences.getString(AGE_KEY, "");

        editTextName.setText(name);
        editTextAge.setText(age);
    }
}