package com.example.lab2.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;
import com.example.lab2.data.api.ApiInterface;
import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.ApiResponse;
import com.example.lab2.data.api.model.Expense;
import com.example.lab2.fragment.DetailExpenseActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpenseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenses = new ArrayList<>();
    private List<Expense> displayedExpenses = new ArrayList<>();  // To store the expenses displayed on the screen
    private int pageSize = 3;  // Number of items to show initially per page
    private int currentIndex = 0;  // Index to track how many items we've loaded

    private boolean isLoading = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_expense_list_fragment, container, false);

        // Optional: Apply edge-to-edge behavior for handling system UI (e.g., status bar)
        View mainView = view.findViewById(R.id.main); // Ensure your root layout has android:id="@+id/main"
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // Initialize RecyclerView and set its layout manager
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with an empty list and attach it to the RecyclerView
        adapter = new ExpenseAdapter(displayedExpenses);
        recyclerView.setAdapter(adapter);

        // Fetch all expenses (no pagination from API)
        fetchExpenses();

        // Set up the scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Check if the user has scrolled to the bottom
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    // If we've reached the bottom, load more items
                    loadMoreExpenses();
                }
            }
        });

        return view;
    }

    /**
     * Fetches all the expenses from the API.
     */
    private void fetchExpenses() {
        isLoading = true;

        ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
        Call<List<Expense>> call = apiInterface.getExpenses();  // Get all expenses (no page query)
        call.enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                isLoading = false;

                if (response.isSuccessful() && response.body() != null) {
                    expenses.clear();  // Clear previous expenses
                    expenses.addAll(response.body());  // Add all fetched expenses

                    // Initially load the first page of expenses
                    loadMoreExpenses();
                } else {
                    Toast.makeText(getActivity(), "Failed to load expenses", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                isLoading = false;
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Load more expenses and update the adapter
     */
    private void loadMoreExpenses() {
        if (currentIndex >= expenses.size()) {
            return;  // No more data to load
        }

        // Determine how many items to load
        int endIndex = Math.min(currentIndex + pageSize, expenses.size());

        // Add the next set of expenses to the displayed list
        for (int i = currentIndex; i < endIndex; i++) {
            displayedExpenses.add(expenses.get(i));
        }

        // Update the displayed list and notify the adapter
        adapter.notifyDataSetChanged();

        // Update the currentIndex to point to the next set of expenses to load
        currentIndex = endIndex;
    }

    // Adapter class for binding expense data to RecyclerView items
    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

        private final List<Expense> expenses;

        public ExpenseAdapter(List<Expense> expenses) {
            this.expenses = expenses;
        }

        @Override
        public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_expense, parent, false);
            return new ExpenseViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ExpenseViewHolder holder, int position) {
            Expense expense = expenses.get(position);

            // Bind expense data to views
            holder.amount.setText(expense.getAmount() + " " + expense.getCurrency());
            holder.category.setText(expense.getCategory());
            holder.remark.setText(expense.getRemark());
            holder.date.setText(expense.getDate());

            // Handle click on the entire item to navigate to DetailExpenseActivity
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), DetailExpenseActivity.class);
                intent.putExtra("expenseId", expense.getId());
                startActivity(intent);
            });

            // Handle delete button click: show confirmation modal before deleting
            holder.deleteButton.setOnClickListener(v -> {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete Expense")
                        .setMessage("Are you sure you want to delete this expense?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Call API to delete the expense if user confirms
                            deleteExpense(expense, position);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        @Override
        public int getItemCount() {
            return expenses.size();
        }

        // ViewHolder class for expense items
        class ExpenseViewHolder extends RecyclerView.ViewHolder {
            TextView amount, category, remark, date;
            ImageButton deleteButton;

            public ExpenseViewHolder(View itemView) {
                super(itemView);
                amount = itemView.findViewById(R.id.amount);
                category = itemView.findViewById(R.id.category);
                remark = itemView.findViewById(R.id.remark);
                date = itemView.findViewById(R.id.date);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }

    /**
     * Calls the API to delete the expense. On success, removes the item from the list and shows a success message.
     *
     * @param expense  the expense to delete
     * @param position the position in the adapter
     */
    private void deleteExpense(Expense expense, int position) {
        ApiInterface apiInterface = RetrofitInstance.getInstance(requireContext()).getApiInterface();
        Call<ApiResponse> call = apiInterface.deleteExpense(expense.getId());
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    // Remove the item from the list and update the adapter
                    expenses.remove(position);
                    displayedExpenses.remove(position);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, expenses.size());
                    Toast.makeText(getActivity(), "Delete success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Failed to delete expense", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
