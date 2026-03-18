package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    Button btnChangeOrder;
    TextView orderText, totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        // Linking UI
        btnChangeOrder = findViewById(R.id.btnChangeOrder);
        orderText = findViewById(R.id.orderText);
        totalText = findViewById(R.id.totalText);

        // Receiving data from MainActivity
        String order = getIntent().getStringExtra("orderDetails");
        int total = getIntent().getIntExtra("totalCost", 0);

        // Null safety
        if (order == null || order.isEmpty()) {
            order = "No items selected";
        }

        // Display order
        orderText.setText(order);
        totalText.setText("Total Cost: ₹" + total);

        // Change order button → Only toast (order locked)
        btnChangeOrder.setOnClickListener(v ->
                Toast.makeText(OrderSummaryActivity.this,
                        "Order placed, can't be reverted :(",
                        Toast.LENGTH_LONG).show()
        );
    }
}
