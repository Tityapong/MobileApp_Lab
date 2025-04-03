//
//
//package com.example.lab2;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.lab2.data.api.RetrofitInstance;
//import com.example.lab2.data.api.model.ApiResponse;
//import com.example.lab2.data.api.model.RegisterRequest;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class RegisterActivity extends AppCompatActivity {
//    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
//    private Button registerButton;
//    private TextView messageTextView, tvAlreadyHaveAccount;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_register);
//
//        // Initialize views
//        nameEditText = findViewById(R.id.et_name);
//        emailEditText = findViewById(R.id.et_new_email);
//        passwordEditText = findViewById(R.id.et_new_password);
//        confirmPasswordEditText = findViewById(R.id.et_confirm_password); // Added confirm password
//        registerButton = findViewById(R.id.btn_sign_up);
//        messageTextView = findViewById(R.id.messageTextView); // Message TextView
//        tvAlreadyHaveAccount = findViewById(R.id.tv_already_have_account);
//
//        // Handle already have an account text click
//        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Start the LoginActivity when clicked
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set click listener for register button
//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = nameEditText.getText().toString().trim();
//                String email = emailEditText.getText().toString().trim();
//                String password = passwordEditText.getText().toString().trim();
//                String confirmPassword = confirmPasswordEditText.getText().toString().trim();
//
//                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
//                    messageTextView.setText("Please fill all fields");
//                    return;
//                }
//
//                if (!password.equals(confirmPassword)) {
//                    messageTextView.setText("Passwords do not match");
//                    return;
//                }
//
//                RegisterRequest registerRequest = new RegisterRequest(name, email, password, confirmPassword);
//                registerUser(registerRequest);
//            }
//        });
//
//        // Apply window insets
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//
//    private void registerUser(RegisterRequest registerRequest) {
//        // Make the API call to register the user
//        Call<ApiResponse> call = RetrofitInstance.getInstance().getApiInterface().registerUser(registerRequest);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ApiResponse apiResponse = response.body();
//                    messageTextView.setText("Registration successful: " + apiResponse.getMessage());
//                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
//                    // Navigate to LoginActivity after successful registration
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                    finish();
//                } else {
//                    messageTextView.setText("Registration failed. Please try again.");
//                    Log.e("API_ERROR", "Registration failed: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                messageTextView.setText("Registration failed: " + t.getMessage());
//                Log.e("API_ERROR", "Registration failed: " + t.getMessage());
//            }
//        });
//    }
//}

package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.ApiResponse;
import com.example.lab2.data.api.model.RegisterRequest;
import com.example.lab2.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView messageTextView, tvAlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialize views
        nameEditText = findViewById(R.id.et_name);
        emailEditText = findViewById(R.id.et_new_email);
        passwordEditText = findViewById(R.id.et_new_password);
        confirmPasswordEditText = findViewById(R.id.et_confirm_password);
        registerButton = findViewById(R.id.btn_sign_up);
        messageTextView = findViewById(R.id.messageTextView);
        tvAlreadyHaveAccount = findViewById(R.id.tv_already_have_account);

        // Handle already have an account text click
        tvAlreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    messageTextView.setText("Please fill all fields");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    messageTextView.setText("Passwords do not match");
                    return;
                }

                RegisterRequest registerRequest = new RegisterRequest(name, email, password, confirmPassword);
                registerUser(registerRequest);
            }
        });

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void registerUser(RegisterRequest registerRequest) {
        // Make the API call to register the user
        Call<ApiResponse> call = RetrofitInstance.getInstance(this).getApiInterface().registerUser(registerRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    messageTextView.setText("Registration successful: " + apiResponse.getMessage());
                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    // Check if the registration response includes a token
                    String token = apiResponse.getAccessToken();
                    if (token != null && !token.isEmpty()) {
                        // Save the token if provided
                        TokenManager tokenManager = new TokenManager(getApplicationContext());
                        tokenManager.saveToken(token);
                        Log.d("RegisterActivity", "Token saved: " + token);
                    }

                    // After successful registration, navigate to LoginActivity
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    messageTextView.setText("Registration failed: " + (response.errorBody() != null ? response.errorBody().toString() : "Unknown error"));
                    Log.e("API_ERROR", "Registration failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                messageTextView.setText("Registration failed: " + t.getMessage());
                Log.e("API_ERROR", "Registration failed: " + t.getMessage());
            }
        });
    }

}