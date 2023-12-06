package com.example.mobileapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBarActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    TransactionsFragment transactionsFragment = new TransactionsFragment();

    BudgetFragment budgetsFragment = new BudgetFragment();
    AccountsFragment accountsFragment = new AccountsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_activity);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountsFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.accounts) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, accountsFragment).commit();
            } else if (item.getItemId() == R.id.transactions) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, transactionsFragment).commit();
            } else if (item.getItemId() == R.id.budgets) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, budgetsFragment).commit();
            }
            return true;
        });

    }
}