package com.example.myapplication;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etItemName, etItemCost;
    private Button btnAdd, btnAddToTotal;
    private Spinner spinnerItems;
    private TextView tvTotalCost;
    private DatabaseHelper databaseHelper;

    private List<String> itemNames;
    private List<Double> itemCosts;
    private double totalCost = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etItemName = findViewById(R.id.etItemName);
        etItemCost = findViewById(R.id.etItemCost);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddToTotal = findViewById(R.id.btnAddToTotal);
        spinnerItems = findViewById(R.id.spinnerItems);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        databaseHelper = new DatabaseHelper(this);
        itemNames = new ArrayList<>();
        itemCosts = new ArrayList<>();

        loadSpinnerData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etItemName.getText().toString().trim();
                String costStr = etItemCost.getText().toString().trim();

                if (name.isEmpty() || costStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter name and cost", Toast.LENGTH_SHORT).show();
                    return;
                }

                double cost = Double.parseDouble(costStr);
                if (databaseHelper.addItem(name, cost)) {
                    Toast.makeText(MainActivity.this, "Item added", Toast.LENGTH_SHORT).show();
                    etItemName.setText("");
                    etItemCost.setText("");
                    loadSpinnerData();
                } else {
                    Toast.makeText(MainActivity.this, "Error adding item", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnAddToTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedPosition = spinnerItems.getSelectedItemPosition();
                if (selectedPosition != Spinner.INVALID_POSITION && !itemCosts.isEmpty()) {
                    double selectedCost = itemCosts.get(selectedPosition);
                    totalCost += selectedCost;
                    tvTotalCost.setText(String.format("Total Cost: $%.2f", totalCost));
                } else {
                    Toast.makeText(MainActivity.this, "No item selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadSpinnerData() {
        itemNames.clear();
        itemCosts.clear();
        Cursor cursor = databaseHelper.getAllItems();

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME));
                double cost = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_COST));
                itemNames.add(name + " ($" + cost + ")");
                itemCosts.add(cost);
            } while (cursor.moveToNext());
        }
        cursor.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItems.setAdapter(adapter);
    }
}
