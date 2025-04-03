package com.example.lab2.data.api;

import com.example.lab2.data.api.model.AddExpenseRequest;
import com.example.lab2.data.api.model.ApiResponse;
import com.example.lab2.data.api.model.Expense;
import com.example.lab2.data.api.model.LoginRequest;
import com.example.lab2.data.api.model.RegisterRequest;
import com.example.lab2.data.api.model.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Headers;

import retrofit2.http.Header;

public interface ApiInterface {
    @POST("/api/register")
    Call<ApiResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/api/login")
    Call<ApiResponse> loginUser(@Body LoginRequest loginRequest);


    @GET("/api/expenses")
    Call<List<Expense>> getExpenses();

    @GET("/api/expenses/{id}")
    Call<Expense> getExpenseById(@Path("id") int expenseId);
    @DELETE("/api/expenses/{id}")
    Call<ApiResponse> deleteExpense(@Path("id") int expenseId);

    @Headers("Accept: application/json")
    @POST("/api/expenses")
    Call<ApiResponse> addExpense(@Body AddExpenseRequest addExpenseRequest);


    @GET("/api/user")
    Call<UserResponse> getUserInfo();
    @POST("/api/logout")
    Call<ApiResponse> logout();
}