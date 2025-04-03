//package com.example.lab2.fragment;
//
//import android.os.Bundle;
//import android.widget.TextView;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.lab2.R;
//import com.example.lab2.service.ExpenseData;
//import com.example.lab2.model.Expense;
//
//public class DetailExpenseActivity extends AppCompatActivity {
//
//    private TextView amountTextView, currencyTextView, categoryTextView, remarkTextView, dateTextView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_expense);
//
//        // Initialize the TextViews
//        amountTextView = findViewById(R.id.amount);
//        currencyTextView = findViewById(R.id.currency);
//        categoryTextView = findViewById(R.id.category);
//        remarkTextView = findViewById(R.id.remark);
//        dateTextView = findViewById(R.id.date);
//
//        // Retrieve the expense ID passed from ExpenseListFragment
//        String expenseId = getIntent().getStringExtra("expenseId");
//
//        // Fetch the expense details using the ID
//        Expense expense = ExpenseData.getExpenseById(expenseId);
//
//        if (expense != null) {
//            // Set the data in the TextViews
//            amountTextView.setText(String.format("%s %.2f", expense.getCurrency(), expense.getAmount()));
//            currencyTextView.setText(expense.getCurrency());
//            categoryTextView.setText(expense.getCategory());
//            remarkTextView.setText(expense.getRemark());
//            dateTextView.setText(expense.getDate().toString());  // Adjust the date format if necessary
//        }
//    }
//}



package com.example.lab2.fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.R;
import com.example.lab2.data.api.ApiInterface;
import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.Expense;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        // Make sure you send the expenseId as an int from the previous screen.
        int expenseId = getIntent().getIntExtra("expenseId", -1);
        if(expenseId != -1) {
            fetchExpenseDetail(expenseId);
        } else {
            Toast.makeText(this, "Invalid expense ID", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Fetches the expense details from the API.
     *
     * @param expenseId The ID of the expense to retrieve.
     */
    private void fetchExpenseDetail(int expenseId) {
        ApiInterface apiInterface = RetrofitInstance.getInstance(this).getApiInterface();
        Call<Expense> call = apiInterface.getExpenseById(expenseId);
        call.enqueue(new Callback<Expense>() {
            @Override
            public void onResponse(Call<Expense> call, Response<Expense> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Expense expense = response.body();
                    // Set the data in the TextViews
                    amountTextView.setText(String.format("%s %.2f", expense.getCurrency(), expense.getAmount()));
                    currencyTextView.setText(expense.getCurrency());
                    categoryTextView.setText(expense.getCategory());
                    remarkTextView.setText(expense.getRemark());
                    dateTextView.setText(expense.getDate());
                } else {
                    Toast.makeText(DetailExpenseActivity.this, "Failed to load expense details", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Expense> call, Throwable t) {
                Toast.makeText(DetailExpenseActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
