//
//
//package com.example.lab2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class addexpense extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_addexpense);
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        EditText amountEditText = findViewById(R.id.editTextText);
//        RadioGroup currencyRadioGroup = findViewById(R.id.currencyRadioGroup);
//        Spinner categorySpinner = findViewById(R.id.categorySpinner);
//        EditText remarkEditText = findViewById(R.id.remarkEditText);
//        Button addExpenseButton = findViewById(R.id.addExpenseButton);
//
//        addExpenseButton.setOnClickListener(view -> {
//            String amount = amountEditText.getText().toString();
//            String currency = currencyRadioGroup.getCheckedRadioButtonId() == R.id.usdRadioButton ? "USD" : "KHR";
//            String category = categorySpinner.getSelectedItem().toString();
//            String remark = remarkEditText.getText().toString();
//
//
//            Intent resultIntent = new Intent();
//            resultIntent.putExtra("amount", amount);
//            resultIntent.putExtra("currency", currency);
//            resultIntent.putExtra("category", category);
//            resultIntent.putExtra("remark", remark);
//
//            setResult(RESULT_OK, resultIntent);
//            finish();
//        });
//    }
//}




package com.example.lab2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

    public class addexpense extends AppCompatActivity {
    private EditText dateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addexpense);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText amountEditText = findViewById(R.id.editTextText);
        RadioGroup currencyRadioGroup = findViewById(R.id.currencyRadioGroup);
        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        EditText remarkEditText = findViewById(R.id.remarkEditText);
        dateEditText = findViewById(R.id.dateEditText);
        Button addExpenseButton = findViewById(R.id.addExpenseButton);

        // Set current date as default
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateEditText.setText(dateFormat.format(new Date()));

        // Show DatePickerDialog when clicking the date field
        dateEditText.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    addexpense.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d",
                                selectedDay, selectedMonth + 1, selectedYear);
                        dateEditText.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        addExpenseButton.setOnClickListener(view -> {
            String amount = amountEditText.getText().toString();
            String currency = currencyRadioGroup.getCheckedRadioButtonId() == R.id.usdRadioButton ? "USD" : "KHR";
            String category = categorySpinner.getSelectedItem().toString();
            String remark = remarkEditText.getText().toString();
            String date = dateEditText.getText().toString();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("amount", amount);
            resultIntent.putExtra("currency", currency);
            resultIntent.putExtra("category", category);
            resultIntent.putExtra("remark", remark);
            resultIntent.putExtra("date", date);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}