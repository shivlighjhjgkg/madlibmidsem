package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox pizza, burger, pasta, coffee;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI elements
        pizza = findViewById(R.id.cbPizza);
        burger = findViewById(R.id.cbBurger);
        pasta = findViewById(R.id.cbPasta);
        coffee = findViewById(R.id.cbCoffee);

        submit = findViewById(R.id.submitBtn);

        submit.setOnClickListener(v -> {

            StringBuilder order = new StringBuilder();
            int total = 0;

            // Checking selected items
            if (pizza.isChecked()) {
                order.append("Pizza - ₹250\n");
                total += 250;
            }

            if (burger.isChecked()) {
                order.append("Burger - ₹180\n");
                total += 180;
            }

            if (pasta.isChecked()) {
                order.append("Pasta - ₹220\n");
                total += 220;
            }

            if (coffee.isChecked()) {
                order.append("Coffee - ₹120\n");
                total += 120;
            }

            // Prevent empty order
            if (total == 0) {
                Toast.makeText(MainActivity.this,
                        "Please select at least one item 🍽️",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            // Disable editing after submit
            pizza.setEnabled(false);
            burger.setEnabled(false);
            pasta.setEnabled(false);
            coffee.setEnabled(false);
            submit.setEnabled(false);

            // Move to summary activity
            Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
            intent.putExtra("orderDetails", order.toString());
            intent.putExtra("totalCost", total);

            startActivity(intent);
        });
    }
}
