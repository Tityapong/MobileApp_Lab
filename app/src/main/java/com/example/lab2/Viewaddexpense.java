
package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Viewaddexpense extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_viewaddexpense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView amountTextView = findViewById(R.id.amountTextView);
        TextView currencyTextView = findViewById(R.id.currencyTextView);
        TextView categoryTextView = findViewById(R.id.categoryTextView);
        TextView remarkTextView = findViewById(R.id.remarkTextView);
        TextView dateTextView = findViewById(R.id.dateTextView);

        Button addNewButton = findViewById(R.id.addNewExpenseButton);
        Button backButton = findViewById(R.id.backToHomeButton);

        Intent intent = getIntent();
        if (intent != null) {
            String amount = intent.getStringExtra("amount");
            String currency = intent.getStringExtra("currency");
            String category = intent.getStringExtra("category");
            String remark = intent.getStringExtra("remark");
            String date = intent.getStringExtra("date");

            if (amount != null) amountTextView.setText("Amount: " + amount);
            if (currency != null) currencyTextView.setText("Currency: " + currency);
            if (category != null) categoryTextView.setText("Category: " + category);
            if (remark != null) remarkTextView.setText("Remark: " + remark);
            if (date != null) dateTextView.setText("Date: " + date);


        }

        addNewButton.setOnClickListener(view -> {
            Intent newIntent = new Intent(Viewaddexpense.this, addexpense.class);
            startActivity(newIntent);
        });

        backButton.setOnClickListener(view -> {
            finish();
        });
    }
}
