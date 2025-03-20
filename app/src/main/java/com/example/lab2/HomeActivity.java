//package com.example.lab2;
//
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.activity.EdgeToEdge;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//
//import com.example.lab2.databinding.ActivityHomeBinding;
//import com.example.lab2.fragment.AddExpenseFragment;
//import com.example.lab2.fragment.ExpenseListFragment;
//import com.example.lab2.fragment.HomeFragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class HomeActivity extends AppCompatActivity {
//
//    private ActivityHomeBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//
//        // Initialize View Binding
//        binding = ActivityHomeBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        // Set up bottom navigation listener
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            Fragment selectedFragment = null;
//            int itemId = item.getItemId();
//
//            if (itemId == R.id.nav_home) {
//                selectedFragment = new HomeFragment();
//            } else if (itemId == R.id.nav_add_expense) {
//                selectedFragment = new AddExpenseFragment();
//            } else if (itemId == R.id.nav_expense_list) {
//                selectedFragment = new ExpenseListFragment();
//            }
//
//            if (selectedFragment != null) {
//                replaceFragment(selectedFragment);
//            }
//            return true;
//        });
//
//        // Set default fragment
//        if (savedInstanceState == null) {
//            binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
//        }
//    }
//
//    private void replaceFragment(Fragment fragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragmentContainer, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        binding = null;
//    }
//}


package com.example.lab2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab2.databinding.ActivityHomeBinding;
import com.example.lab2.fragment.AddExpenseFragment;
import com.example.lab2.fragment.ExpenseListFragment;
import com.example.lab2.fragment.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize View Binding
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up bottom navigation listener
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                // Create the HomeFragment instance and pass data if necessary
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_add_expense) {
                selectedFragment = new AddExpenseFragment();
            } else if (itemId == R.id.nav_expense_list) {
                selectedFragment = new ExpenseListFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

        // Set default fragment
        if (savedInstanceState == null) {
            binding.bottomNavigation.setSelectedItemId(R.id.nav_home);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);  // Add to back stack if needed
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
