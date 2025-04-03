//package com.example.lab2.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.fragment.app.Fragment;
//
//import com.example.lab2.LoginActivity;
//import com.example.lab2.R;
//import com.example.lab2.data.api.RetrofitInstance;
//import com.example.lab2.data.api.model.UserResponse;
//import com.example.lab2.utils.TokenManager;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class ProfileFragment extends Fragment {
//
//    private TextView textViewName, textViewEmail;
//    private Button buttonLogout;
//    private TokenManager tokenManager;
//
//    public ProfileFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_profile, container, false);
//
//        // Initialize views
//        textViewName = view.findViewById(R.id.textViewName);
//        textViewEmail = view.findViewById(R.id.textViewEmail);
//        buttonLogout = view.findViewById(R.id.buttonLogout);
//
//        // Initialize TokenManager
//        tokenManager = new TokenManager(requireContext());
//
//        // Fetch user info
//        fetchUserInfo();
//
//        // Handle logout
//        buttonLogout.setOnClickListener(v -> {
//            tokenManager.clearToken();
//            Intent intent = new Intent(requireActivity(), LoginActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            requireActivity().finish();
//        });
//
//        return view;
//    }
//
//    private void fetchUserInfo() {
//        // Retrieve the token for debugging
//        String token = tokenManager.getToken();
//        if (token != null) {
//            Log.d("ProfileFragment", "Token retrieved: " + token); // Debug log
//            Call<UserResponse> call = RetrofitInstance.getInstance(requireContext()).getApiInterface().getUserInfo();
//            call.enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        UserResponse userResponse = response.body();
//                        textViewName.setText(userResponse.getName());
//                        textViewEmail.setText(userResponse.getEmail());
//                    } else {
//                        Toast.makeText(getContext(), "Failed to fetch user info: " + response.code(), Toast.LENGTH_SHORT).show();
//                        if (response.code() == 401) { // Unauthorized
//                            tokenManager.clearToken();
//                            startActivity(new Intent(requireActivity(), LoginActivity.class));
//                            requireActivity().finish();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } else {
//            Toast.makeText(getContext(), "Token not available", Toast.LENGTH_SHORT).show();
//            Log.d("ProfileFragment", "Token not available. Please check the token saving process.");
//            // Redirect to LoginActivity if token is missing
//            startActivity(new Intent(requireActivity(), LoginActivity.class));
//            requireActivity().finish();
//        }
//    }
//}


package com.example.lab2.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.lab2.LoginActivity;
import com.example.lab2.R;
import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.UserResponse;
import com.example.lab2.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView textViewName, textViewEmail;
    private Button buttonLogout;
    private TokenManager tokenManager;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize views
        textViewName = view.findViewById(R.id.textViewName);
        textViewEmail = view.findViewById(R.id.textViewEmail);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        // Initialize TokenManager
        tokenManager = new TokenManager(requireContext());

        // Fetch user info
        fetchUserInfo();

        // Handle logout with confirmation modal
        buttonLogout.setOnClickListener(v -> {
            showLogoutConfirmationDialog();
        });

        return view;
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform logout
                        performLogout();
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void performLogout() {
        // Clear the token
        tokenManager.clearToken();
        Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to LoginActivity
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }

    private void fetchUserInfo() {
        // Retrieve the token for debugging
        String token = tokenManager.getToken();
        if (token != null) {
            Log.d("ProfileFragment", "Token retrieved: " + token); // Debug log
            Call<UserResponse> call = RetrofitInstance.getInstance(requireContext()).getApiInterface().getUserInfo();
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        UserResponse userResponse = response.body();
                        textViewName.setText(userResponse.getName());
                        textViewEmail.setText(userResponse.getEmail());
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch user info: " + response.code(), Toast.LENGTH_SHORT).show();
                        if (response.code() == 401) { // Unauthorized
                            tokenManager.clearToken();
                            startActivity(new Intent(requireActivity(), LoginActivity.class));
                            requireActivity().finish();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Token not available", Toast.LENGTH_SHORT).show();
            Log.d("ProfileFragment", "Token not available. Please check the token saving process.");
            // Redirect to LoginActivity if token is missing
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        }
    }
}