//package com.example.lab2;
//
//import android.os.Bundle;
//
//
//import android.content.Intent;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class LoginActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_login);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Set up click listener for "Create New Account"
//        TextView tvCreateNewAccount = findViewById(R.id.tvCreateNewAccount);
//        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });
//
//
//        Button btnSignIn = findViewById(R.id.btn_sign_in);
//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // When Sign In button is clicked, navigate to HomeActivity
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }
//}
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
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.lab2.data.api.RetrofitInstance;
//import com.example.lab2.data.api.model.ApiResponse;
//import com.example.lab2.data.api.model.LoginRequest;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class LoginActivity extends AppCompatActivity {
//    private EditText emailEditText, passwordEditText;
//    private Button loginButton;
//    private TextView messageTextView, tvCreateNewAccount;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // Initialize views
//        emailEditText = findViewById(R.id.et_email);
//        passwordEditText = findViewById(R.id.et_password);
//        loginButton = findViewById(R.id.btn_sign_in);
//        messageTextView = findViewById(R.id.messageTextView); // Ensure it's initialized correctly
//        tvCreateNewAccount = findViewById(R.id.tvCreateNewAccount);
//
//        // Handle "Create New Account" text click
//        tvCreateNewAccount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });
//
//        // Handle login button click
//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = emailEditText.getText().toString().trim();
//                String password = passwordEditText.getText().toString().trim();
//
//                if (email.isEmpty() || password.isEmpty()) {
//                    messageTextView.setText("Please fill email and password");
//                    return;
//                }
//
//                LoginRequest loginRequest = new LoginRequest(email, password);
//                loginUser(loginRequest);
//            }
//        });
//    }
//
//    private void loginUser(LoginRequest loginRequest) {
//        Call<ApiResponse> call = RetrofitInstance.getInstance().getApiInterface().loginUser(loginRequest);
//        call.enqueue(new Callback<ApiResponse>() {
//            @Override
//            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    ApiResponse apiResponse = response.body();
//                    messageTextView.setText("Login successful: " + apiResponse.getMessage());
//                    Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
//                    // Navigate to HomeActivity after successful login
//                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
//                    finish();
//                } else {
//                    messageTextView.setText("Login failed: " + (response.errorBody() != null ? response.errorBody().toString() : "Unknown error"));
//                    Log.e("API_ERROR", "Login failed: " + response.code());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ApiResponse> call, Throwable t) {
//                messageTextView.setText("Login failed: " + t.getMessage());
//                Log.e("API_ERROR", "Login failed: " + t.getMessage());
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

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.data.api.RetrofitInstance;
import com.example.lab2.data.api.model.ApiResponse;
import com.example.lab2.data.api.model.LoginRequest;
import com.example.lab2.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView messageTextView, tvCreateNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.et_password);
        loginButton = findViewById(R.id.btn_sign_in);
        messageTextView = findViewById(R.id.messageTextView);
        tvCreateNewAccount = findViewById(R.id.tvCreateNewAccount);

        // Handle "Create New Account" text click
        tvCreateNewAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        // Handle login button click
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                messageTextView.setText("Please fill email and password");
                return;
            }

            LoginRequest loginRequest = new LoginRequest(email, password);
            loginUser(loginRequest);
        });
    }

    private void loginUser(LoginRequest loginRequest) {
        Call<ApiResponse> call = RetrofitInstance.getInstance(this).getApiInterface().loginUser(loginRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    String token = apiResponse.getAccessToken(); // Use the correct getter

                    if (token != null && !token.isEmpty()) {
                        // Save the token
                        TokenManager tokenManager = new TokenManager(getApplicationContext());
                        tokenManager.saveToken(token);
                        Log.d("LoginActivity", "Token saved: " + token); // Debug log

                        messageTextView.setText("Login successful: " + apiResponse.getMessage());
                        Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                        // Navigate to HomeActivity after successful login
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        messageTextView.setText("Login failed: No token received");
                        Log.e("API_ERROR", "No token in response");
                    }
                } else {
                    messageTextView.setText("Login failed: " + (response.errorBody() != null ? response.errorBody().toString() : "Unknown error"));
                    Log.e("API_ERROR", "Login failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                messageTextView.setText("Login failed: " + t.getMessage());
                Log.e("API_ERROR", "Login failed: " + t.getMessage());
            }
        });
    }
}