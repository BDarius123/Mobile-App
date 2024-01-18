package com.example.mobileapp.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Transaction implements Serializable {
    private String id;
    private String category;
    private String account;
    private String date;
    private String memo;
    private double amount;

    public Transaction(String id, String category, String account, String date, String memo, double amount) {
        this.id = id;
        this.category = category;
        this.account = account;
        this.date = date;
        this.memo = memo;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "category='" + category + '\'' +
                ", account='" + account + '\'' +
                ", date=" + date +
                ", memo='" + memo + '\'' +
                ", amount=" + amount +
                '}';
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
