package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ZoomActivity extends AppCompatActivity {

    private ImageView imageView;
    private float scaleX = 1.0f;
    private float scaleY = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);

        imageView = findViewById(R.id.imageView);
        Button btnZoomIn = findViewById(R.id.btnZoomIn);
        Button btnZoomOut = findViewById(R.id.btnZoomOut);

        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scaleX += 0.2f;
                scaleY += 0.2f;
                imageView.setScaleX(scaleX);
                imageView.setScaleY(scaleY);
            }
        });

        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (scaleX > 0.2f) {
                    scaleX -= 0.2f;
                    scaleY -= 0.2f;
                    imageView.setScaleX(scaleX);
                    imageView.setScaleY(scaleY);
                }
            }
        });
    }
}