package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView sportsList;

    String[] sports = {
            "Cricket",
            "Football",
            "Basketball",
            "Tennis",
            "Badminton",
            "Hockey"
    };

    String[] sportMessages = {
            "Cricket is a bat and ball game played between two teams.",
            "Football is the world's most popular sport played with a spherical ball.",
            "Basketball involves shooting a ball through a hoop.",
            "Tennis is played with rackets and a ball across a net.",
            "Badminton is a fast racket sport played using a shuttlecock.",
            "Hockey is played with sticks and a ball or puck."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sportsList = findViewById(R.id.sportsList);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.list_item,
                R.id.listText,
                sports
        );


        sportsList.setAdapter(adapter);

        sportsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(
                        MainActivity.this,
                        sportMessages[position],
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
