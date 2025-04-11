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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.lab2.data.api.model.ApiResponse;
import com.example.lab2.data.api.model.Category;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_expense_fragment, container, false);

        // Initialize the UI components
        amountEditText = view.findViewById(R.id.editTextText);
        currencyRadioGroup = view.findViewById(R.id.currencyRadioGroup);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        remarkEditText = view.findViewById(R.id.remarkEditText);
        dateEditText = view.findViewById(R.id.dateEditText);
        addExpenseButton = view.findViewById(R.id.addExpenseButton);

        // Set default date
        setDefaultDate();

        // Apply edge-to-edge behavior for system UI
        ViewCompat.setOnApplyWindowInsetsListener(view.findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set the date picker dialog
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

        // Fetch categories dynamically from the API
        fetchCategories();

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

            // Get the selected category_id
            int categoryId = ((Category) categorySpinner.getSelectedItem()).getId();

            String remark = remarkEditText.getText().toString().trim();
            String date = dateEditText.getText().toString();

            // Create the AddExpenseRequest with category_id
            AddExpenseRequest request = new AddExpenseRequest(amount, currency, categoryId, remark, date);

            // Call the API to add the expense
            ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
            Call<ApiResponse> call = apiInterface.addExpense(request);
            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                        clearForm();
                    } else {
                        Toast.makeText(getActivity(), "Failed to add expense", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Set up the ImageButton for adding a new category
        ImageButton addCategoryButton = view.findViewById(R.id.imageButton);
        addCategoryButton.setOnClickListener(v -> {
            // Show an alert dialog to add a new category
            showAddCategoryDialog();
        });

        return view;
    }

    private void setDefaultDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateEditText.setText(dateFormat.format(new Date()));
    }

    private void clearForm() {
        amountEditText.setText("");
        remarkEditText.setText("");
        categorySpinner.setSelection(0);
        setDefaultDate();
    }

    private void fetchCategories() {
        ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
        Call<List<Category>> call = apiInterface.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful()) {
                    categories = response.body();
                    updateCategorySpinner();
                } else {
                    Toast.makeText(getActivity(), "Failed to load categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCategorySpinner() {
        if (categories != null && !categories.isEmpty()) {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(adapter);
        }
    }

    private void showAddCategoryDialog() {
        EditText input = new EditText(getActivity());
        input.setHint("Enter new category");

        new AlertDialog.Builder(getActivity())
                .setTitle("Add New Category")
                .setMessage("Please enter the name of the new category")
                .setView(input)
                .setPositiveButton("Submit", (dialog, which) -> {
                    String categoryName = input.getText().toString().trim();
                    if (categoryName.isEmpty()) {
                        Toast.makeText(getActivity(), "Category name cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        addCategory(categoryName);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    private void addCategory(String categoryName) {
        ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
        Category newCategory = new Category(0, categoryName);  // Now the constructor accepts (id, name)
        Call<ApiResponse> call = apiInterface.addCategory(newCategory);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Category added successfully", Toast.LENGTH_SHORT).show();
                    fetchCategories();
                } else {
                    Toast.makeText(getActivity(), "Failed to add category", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
