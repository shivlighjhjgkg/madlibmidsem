package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SeekbarActivity extends AppCompatActivity {

    private int age = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seekbar);

        TextView tvAge = findViewById(R.id.tvAge);
        SeekBar seekBar = findViewById(R.id.seekBar);
        Button btnOkSeek = findViewById(R.id.btnOkSeek);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                age = progress;
                tvAge.setText("Select Age: " + age);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        btnOkSeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (age > 5) {
                    Intent intent = new Intent(SeekbarActivity.this, ToggleActivity.class);
                    startActivity(intent);
                } else {
                    finish(); // Go back
                }
            }
        });
    }
}