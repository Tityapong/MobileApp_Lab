//package com.example.lab2.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.lab2.R;
//
//public class AddExpenseFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_add_expense_fragment, container, false);
//
//        // Apply EdgeToEdge behavior (for handling system UI like status bar)
//        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        return view;
//    }
//}



package com.example.lab2.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.lab2.R;
import com.example.lab2.data.api.ApiInterface;
import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.AddExpenseRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExpenseFragment extends Fragment {

    private EditText dateEditText;
    private EditText amountEditText;
    private EditText remarkEditText;
    private RadioGroup currencyRadioGroup;
    private Spinner categorySpinner;
    private Button addExpenseButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_expense_fragment, container, false);

        // Apply edge-to-edge behavior (for handling system UI like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the UI components
        amountEditText = view.findViewById(R.id.editTextText);
        currencyRadioGroup = view.findViewById(R.id.currencyRadioGroup);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        remarkEditText = view.findViewById(R.id.remarkEditText);
        dateEditText = view.findViewById(R.id.dateEditText);
        addExpenseButton = view.findViewById(R.id.addExpenseButton);

        // Set current date as default in "yyyy-MM-dd" format
        setDefaultDate();

        // Show DatePickerDialog when clicking the date field and format as "yyyy-MM-dd"
        dateEditText.setOnClickListener(v -> {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            int year = calendar.get(java.util.Calendar.YEAR);
            int month = calendar.get(java.util.Calendar.MONTH);
            int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

            new android.app.DatePickerDialog(
                    getActivity(),
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d",
                                selectedYear, selectedMonth + 1, selectedDay);
                        dateEditText.setText(selectedDate);
                    },
                    year, month, day).show();
        });

        // Set click listener for the add expense button
        addExpenseButton.setOnClickListener(v -> {
            String amountStr = amountEditText.getText().toString().trim();
            if (amountStr.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }
            double amount = Double.parseDouble(amountStr);

            // Check which currency radio button is selected
            String currency;
            RadioButton usdRadioButton = view.findViewById(R.id.usdRadioButton);
            if (usdRadioButton.isChecked()) {
                currency = "USD";
            } else {
                currency = "KHR";
            }

            String category = categorySpinner.getSelectedItem().toString();
            String remark = remarkEditText.getText().toString().trim();
            String date = dateEditText.getText().toString();

            // Create the AddExpenseRequest
            AddExpenseRequest request = new AddExpenseRequest(amount, currency, category, remark, date);

            // Call the API to add the expense
            ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
            Call<com.example.lab2.data.api.model.ApiResponse> call = apiInterface.addExpense(request);
            call.enqueue(new Callback<com.example.lab2.data.api.model.ApiResponse>() {
                @Override
                public void onResponse(Call<com.example.lab2.data.api.model.ApiResponse> call, Response<com.example.lab2.data.api.model.ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Add successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                    } else {
                        Toast.makeText(getActivity(), "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<com.example.lab2.data.api.model.ApiResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

    /**
     * Sets the default date in the dateEditText field.
     */
    private void setDefaultDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateEditText.setText(dateFormat.format(new Date()));
    }

    /**
     * Clears the form fields and resets the default date.
     */
    private void clearForm() {
        amountEditText.setText("");
        // Optionally, reset currencyRadioGroup if needed:
        // currencyRadioGroup.clearCheck();
        remarkEditText.setText("");
        // Optionally, reset categorySpinner to its first item:
        categorySpinner.setSelection(0);
        setDefaultDate();
    }
}
