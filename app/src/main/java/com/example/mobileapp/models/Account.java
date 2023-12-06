package com.example.mobileapp.models;

import java.io.Serializable;

public class Account implements Serializable {
    private String name;
    private double balance;
    private String type;

    public Account(String name, double balance, String type) {
        this.name = name;
        this.balance = balance;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getType() {
        return type;
    }
}
