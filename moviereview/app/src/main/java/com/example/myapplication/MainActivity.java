package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    EditText editTextName, editTextYear, editTextRating;
    Button buttonSave;
    ListView listViewMovies;
    TableLayout tableLayoutDetails;
    TextView tableName, tableYear, tableRating;
    ArrayList<Movie> movieList;
    ArrayList<String> movieNames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextYear = findViewById(R.id.editTextYear);
        editTextRating = findViewById(R.id.editTextRating);
        buttonSave = findViewById(R.id.buttonSave);
        listViewMovies = findViewById(R.id.listViewMovies);
        tableLayoutDetails = findViewById(R.id.tableLayoutDetails);
        tableName = findViewById(R.id.tableName);
        tableYear = findViewById(R.id.tableYear);
        tableRating = findViewById(R.id.tableRating);

        movieList = new ArrayList<>();
        movieNames = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieNames);
        listViewMovies.setAdapter(adapter);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMovie();
            }
        });

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayMovieDetails(position);
            }
        });

        loadMovies();
    }

    private void saveMovie() {
        String name = editTextName.getText().toString().trim();
        String year = editTextYear.getText().toString().trim();
        String rating = editTextRating.getText().toString().trim();

        if (name.isEmpty() || year.isEmpty() || rating.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int ratingVal = Integer.parseInt(rating);
        if (ratingVal < 1 || ratingVal > 5) {
            Toast.makeText(this, "Rating must be between 1 and 5", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = dbHelper.insertData(name, year, rating);
        if (isInserted) {
            Toast.makeText(this, "Review Saved", Toast.LENGTH_SHORT).show();
            editTextName.setText("");
            editTextYear.setText("");
            editTextRating.setText("");
            loadMovies();
        } else {
            Toast.makeText(this, "Error saving review", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadMovies() {
        movieList.clear();
        movieNames.clear();
        Cursor cursor = dbHelper.getAllData();
        if (cursor.getCount() == 0) {
            adapter.notifyDataSetChanged();
            return;
        }

        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            String year = cursor.getString(2);
            String rating = cursor.getString(3);
            movieList.add(new Movie(name, year, rating));
            movieNames.add(name);
        }
        adapter.notifyDataSetChanged();
    }

    private void displayMovieDetails(int position) {
        Movie movie = movieList.get(position);
        tableName.setText(movie.getName());
        tableYear.setText(movie.getYear());
        tableRating.setText(movie.getRating());
        tableLayoutDetails.setVisibility(View.VISIBLE);
    }
}
