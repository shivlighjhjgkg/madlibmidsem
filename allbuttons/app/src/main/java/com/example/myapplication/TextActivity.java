package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        EditText editText = findViewById(R.id.editText);
        Button btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString().trim();
                if (input.equalsIgnoreCase("no")) {
                    Intent intent = new Intent(TextActivity.this, SeekbarActivity.class);
                    startActivity(intent);
                } else {
                    finish(); // Goes back to the previous page
                }
            }
        });
    }
}