package com.example.mobileapp.models;

import java.io.Serializable;

public class Account implements Serializable {
    private String id;
    private String name;
    private double balance;
    private String type;

    public Account(String id,String name, double balance, String type) {
        this.name = name;
        this.balance = balance;
        this.type = type;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
