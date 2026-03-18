package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnCupcake).setOnClickListener(v ->
                showToast("Android Cupcake", R.drawable.android_cupcake));

        findViewById(R.id.btnLollipop).setOnClickListener(v ->
                showToast("Android Lollipop", R.drawable.android_lollipop));

        findViewById(R.id.btnMarshmallow).setOnClickListener(v ->
                showToast("Android Marshmallow", R.drawable.android_marshmallow));

        findViewById(R.id.btnNougat).setOnClickListener(v ->
                showToast("Android Nougat", R.drawable.android_nougat));

        findViewById(R.id.btnOreo).setOnClickListener(v ->
                showToast("Android Oreo", R.drawable.android_oreo));
    }

    private void showToast(String text, int imageRes) {

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, null);

        ImageView img = layout.findViewById(R.id.toastImage);
        TextView txt = layout.findViewById(R.id.toastText);

        img.setImageResource(imageRes);
        txt.setText(text);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
