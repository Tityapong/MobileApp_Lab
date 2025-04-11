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

//
//package com.example.lab2;
//
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentTransaction;
//import androidx.appcompat.app.AlertDialog;
//import android.content.Intent;
//import android.content.DialogInterface;
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
//                // Create the HomeFragment instance and pass data if necessary
//                selectedFragment = new HomeFragment();
//            } else if (itemId == R.id.nav_add_expense) {
//                selectedFragment = new AddExpenseFragment();
//            } else if (itemId == R.id.nav_expense_list) {
//                selectedFragment = new ExpenseListFragment();
//            }else if (itemId == R.id.nav_logout) {
//                // Show logout confirmation dialog
//                showLogoutDialog();
//                return true; // Return true to indicate the item was handled
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
//        transaction.addToBackStack(null);  // Add to back stack if needed
//        transaction.commit();
//    }
//
//    private void showLogoutDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Logout");
//        builder.setMessage("Are you sure you want to logout?");
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Perform logout action (e.g., navigate to LoginActivity)
//                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
//                startActivity(intent);
//                finish(); // Close HomeActivity
//            }
//        });
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss(); // Close the dialog, stay on current screen
//            }
//        });
//        builder.setCancelable(true); // Allow dialog to be dismissed by back button
//        AlertDialog dialog = builder.create();
//        dialog.show();
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
import com.example.lab2.fragment.ProfileFragment;
import com.example.lab2.fragment.SettingFragment; // Import the SettingFragment

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
                selectedFragment = new HomeFragment();
            } else if (itemId == R.id.nav_add_expense) {
                selectedFragment = new AddExpenseFragment();
            } else if (itemId == R.id.nav_expense_list) {
                selectedFragment = new ExpenseListFragment();
            } else if (itemId == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            } else if (itemId == R.id.nav_settings) { // Handle Settings item
                selectedFragment = new SettingFragment(); // Navigate to SettingFragment
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
