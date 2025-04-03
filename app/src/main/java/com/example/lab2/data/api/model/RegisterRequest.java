package com.example.lab2.data.api.model;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String password_confirmation;

    public RegisterRequest(String name, String email, String password, String password_confirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return password_confirmation;
    }
}
