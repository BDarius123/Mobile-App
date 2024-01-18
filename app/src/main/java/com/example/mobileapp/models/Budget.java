package com.example.mobileapp.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


public class Budget {
    private String id;

    private LocalDate date;

    private String memo;

    private double amount;

    private String account;

    private String categorie;

    public Budget(String id, LocalDate date, String memo, double amount, String account, String categorie) {
        this.id = id;
        this.date = date;
        this.memo = memo;
        this.amount = amount;
        this.account = account;
        this.categorie = categorie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
