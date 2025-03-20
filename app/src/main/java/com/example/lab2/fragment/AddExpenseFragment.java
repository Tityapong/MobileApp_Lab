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

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddExpenseFragment extends Fragment {

    private EditText dateEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_expense_fragment, container, false);

        // Apply EdgeToEdge behavior (for handling system UI like status bar)
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the UI components
        EditText amountEditText = view.findViewById(R.id.editTextText);
        RadioGroup currencyRadioGroup = view.findViewById(R.id.currencyRadioGroup);
        Spinner categorySpinner = view.findViewById(R.id.categorySpinner);
        EditText remarkEditText = view.findViewById(R.id.remarkEditText);
        dateEditText = view.findViewById(R.id.dateEditText);
        Button addExpenseButton = view.findViewById(R.id.addExpenseButton);

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
                    getActivity(), // Context is provided by the fragment's host activity
                    (view1, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d",
                                selectedDay, selectedMonth + 1, selectedYear);
                        dateEditText.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Inside AddExpenseFragment
        addExpenseButton.setOnClickListener(view1 -> {
            String amount = amountEditText.getText().toString();
            String currency = currencyRadioGroup.getCheckedRadioButtonId() == R.id.usdRadioButton ? "USD" : "KHR";
            String category = categorySpinner.getSelectedItem().toString();
            String remark = remarkEditText.getText().toString();
            String date = dateEditText.getText().toString();

            // Create a bundle to store the data
            Bundle data = new Bundle();
            data.putString("amount", amount);
            data.putString("currency", currency);
            data.putString("category", category);
            data.putString("remark", remark);
            data.putString("date", date);

            // Pass the data to the HomeFragment using setArguments
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(data);  // Use setArguments to pass data to HomeFragment

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, homeFragment)  // Replace fragment with HomeFragment
                    .commit();
        });

        return view;
    }
}
