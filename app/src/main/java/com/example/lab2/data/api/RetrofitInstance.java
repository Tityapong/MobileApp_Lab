

package com.example.lab2.data.api;

import android.content.Context;

import com.example.lab2.utils.TokenManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static RetrofitInstance instance;
    private ApiInterface apiInterface;

    private RetrofitInstance(Context context) {
        TokenManager tokenManager = new TokenManager(context);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor tokenInterceptor = chain -> {
            Request originalRequest = chain.request();
            String url = originalRequest.url().toString();
            if (url.endsWith("/api/login") || url.endsWith("/api/register")) {
                return chain.proceed(originalRequest);
            }

            String token = tokenManager.getToken();
            if (token != null) {
                Request modifiedRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(modifiedRequest);
            }
            return chain.proceed(originalRequest);
        };

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static RetrofitInstance getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitInstance(context.getApplicationContext());
        }
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }
}