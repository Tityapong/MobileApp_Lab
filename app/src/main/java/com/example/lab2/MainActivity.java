package com.example.lab2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab2.utils.TokenManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if token is available
        TokenManager tokenManager = new TokenManager(getApplicationContext());
        String token = tokenManager.getToken();

        if (token != null && !token.isEmpty()) {
            // If token exists, navigate to HomeActivity
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
        } else {
            // If no token, navigate to LoginActivity
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        // Finish MainActivity so it doesn't stay in the back stack
        finish();
    }
}
