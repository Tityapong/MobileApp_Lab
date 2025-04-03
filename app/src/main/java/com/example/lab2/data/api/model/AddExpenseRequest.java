package com.example.lab2.data.api.model;

public class AddExpenseRequest {
    private double amount;
    private String currency;
    private String category;
    private String remark;
    private String date;

    public AddExpenseRequest(double amount, String currency, String category, String remark, String date) {
        this.amount = amount;
        this.currency = currency;
        this.category = category;
        this.remark = remark;
        this.date = date;
    }

    // Getters and setters (if needed)
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
