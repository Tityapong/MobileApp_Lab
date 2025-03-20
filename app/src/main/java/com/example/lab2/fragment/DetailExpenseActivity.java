package com.example.lab2.fragment;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.R;
import com.example.lab2.service.ExpenseData;
import com.example.lab2.model.Expense;

public class DetailExpenseActivity extends AppCompatActivity {

    private TextView amountTextView, currencyTextView, categoryTextView, remarkTextView, dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_expense);

        // Initialize the TextViews
        amountTextView = findViewById(R.id.amount);
        currencyTextView = findViewById(R.id.currency);
        categoryTextView = findViewById(R.id.category);
        remarkTextView = findViewById(R.id.remark);
        dateTextView = findViewById(R.id.date);

        // Retrieve the expense ID passed from ExpenseListFragment
        String expenseId = getIntent().getStringExtra("expenseId");

        // Fetch the expense details using the ID
        Expense expense = ExpenseData.getExpenseById(expenseId);

        if (expense != null) {
            // Set the data in the TextViews
            amountTextView.setText(String.format("%s %.2f", expense.getCurrency(), expense.getAmount()));
            currencyTextView.setText(expense.getCurrency());
            categoryTextView.setText(expense.getCategory());
            remarkTextView.setText(expense.getRemark());
            dateTextView.setText(expense.getDate().toString());  // Adjust the date format if necessary
        }
    }
}
