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
//public class ExpenseListFragment extends Fragment {
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.activity_expense_list_fragment, container, false);
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

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;  // <-- Missing import
import android.view.View;  // <-- Missing import
import android.view.ViewGroup;  // <-- Missing import
import android.widget.TextView;  // <-- Missing import
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;
import com.example.lab2.model.Expense;
import com.example.lab2.service.ExpenseData;
import com.example.lab2.fragment.DetailExpenseActivity;  // <-- Update the import path for DetailExpenseActivity

import java.text.SimpleDateFormat;
import java.util.List;

public class ExpenseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private List<Expense> expenses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_expense_list_fragment, container, false);

        // Initialize RecyclerView and set its layout manager
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Get the list of expenses
        expenses = ExpenseData.getDummyExpenses();

        // Initialize the adapter and set it to the RecyclerView
        adapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(adapter);

        return view;
    }

    // ExpenseAdapter to bind data to RecyclerView
    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

        private List<Expense> expenses;

        public ExpenseAdapter(List<Expense> expenses) {
            this.expenses = expenses;
        }

        @Override
        public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
            return new ExpenseViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExpenseViewHolder holder, int position) {
            Expense expense = expenses.get(position);
            holder.amount.setText(expense.getAmount() + " " + expense.getCurrency());
            holder.category.setText(expense.getCategory());
            holder.remark.setText(expense.getRemark());
            holder.date.setText(new SimpleDateFormat("yyyy-MM-dd").format(expense.getDate()));

            // Navigate to DetailExpenseActivity when an expense is clicked
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(holder.itemView.getContext(), com.example.lab2.fragment.DetailExpenseActivity.class);
                intent.putExtra("expenseId", expense.getId());
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return expenses.size();
        }

        public class ExpenseViewHolder extends RecyclerView.ViewHolder {
            TextView amount, category, remark, date;

            public ExpenseViewHolder(View itemView) {
                super(itemView);
                amount = itemView.findViewById(R.id.amount);
                category = itemView.findViewById(R.id.category);
                remark = itemView.findViewById(R.id.remark);
                date = itemView.findViewById(R.id.date);
            }
        }
    }
}
