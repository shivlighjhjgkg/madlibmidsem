package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button normalButton;
    ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        normalButton = findViewById(R.id.normalButton);
        toggleButton = findViewById(R.id.toggleButton);

        normalButton.setOnClickListener(v -> {
            showCustomToast("Normal Button Clicked", R.drawable.toast_button);
        });

        toggleButton.setOnClickListener(v -> {

            if (toggleButton.isChecked()) {
                showCustomToast("Toggle Turned ON", R.drawable.toast_toggle);
            } else {
                showCustomToast("Toggle Turned OFF", R.drawable.toast_toggle);
            }

        });
    }

    private void showCustomToast(String message, int imageRes) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        ImageView img = layout.findViewById(R.id.toastImage);
        TextView txt = layout.findViewById(R.id.toastText);

        img.setImageResource(imageRes);
        txt.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
