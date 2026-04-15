package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckboxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox);

        CheckBox[] checkBoxes = new CheckBox[]{
                findViewById(R.id.cb1),
                findViewById(R.id.cb2),
                findViewById(R.id.cb3),
                findViewById(R.id.cb4),
                findViewById(R.id.cb5),
                findViewById(R.id.cb6),
                findViewById(R.id.cb7)
        };

        Button btnRestart = findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                for (CheckBox cb : checkBoxes) {
                    if (cb.isChecked()) {
                        count++;
                    }
                }

                if (count > 5) {
                    // Restart App: Go back to MainActivity and clear the stack
                    Intent intent = new Intent(CheckboxActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CheckboxActivity.this, "Select more than 5 to restart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}