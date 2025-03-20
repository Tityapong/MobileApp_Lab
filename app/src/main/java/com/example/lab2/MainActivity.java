
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

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ADD_EXPENSE = 1;
    private TextView lastExpenseTextView;
    // Store expense data as member variables
    private String latestAmount;
    private String latestCurrency;
    private String latestCategory;
    private String latestRemark;
    private String latestDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lastExpenseTextView = findViewById(R.id.lastExpenseTextView);

        Button addExpenseButton = findViewById(R.id.addExpenseButton);
        addExpenseButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, addexpense.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_EXPENSE);
        });

        Button viewExpensesButton = findViewById(R.id.viewDetailButton);
        viewExpensesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Viewaddexpense.class);
            // Pass all expense data
            intent.putExtra("amount", latestAmount);
            intent.putExtra("currency", latestCurrency);
            intent.putExtra("category", latestCategory);
            intent.putExtra("remark", latestRemark);
            intent.putExtra("date", latestDate);
            startActivity(intent);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_EXPENSE && resultCode == RESULT_OK && data != null) {
            // Get all expense data
            latestAmount = data.getStringExtra("amount");
            latestCurrency = data.getStringExtra("currency");
            latestCategory = data.getStringExtra("category");
            latestRemark = data.getStringExtra("remark");
            latestDate = data.getStringExtra("date");

            // Update the TextView with amount and currency
            if (latestAmount != null && latestCurrency != null) {
                lastExpenseTextView.setText("My last expense was " + latestAmount + " " + latestCurrency);
            }
        }
    }
}  // Only one closing brace here

