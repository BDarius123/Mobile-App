package com.example.mobileapp;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.adapters.TransactionsAdapter;
import com.example.mobileapp.models.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private List<Transaction> transactions;
    private TransactionsAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View rootView = inflater.inflate(R.layout.transactions_fragment, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Transactions");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        transactions = new ArrayList<>();

        Button btnAddTransaction = rootView.findViewById(R.id.btnAddTransaction);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input (replace with your UI elements)
                String category = "Travel Expenses"; // Example category
                String account = "Savings Account"; // Example account
                double amount = 50.0; // Example amount
                String date = "Date : ";// Example date
                String memo = "This is an optional memo"; // Example memo

                // Create a new transaction
                Transaction newTransaction = new Transaction(category, account, date, amount, memo);

                // Add the transaction to the list
                transactions.add(newTransaction);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();

            }
        });

        String[] typeOfCategory = {"Groceries", "Entertainment", "Transportation", "Travel Expenses", "Utilities", "Other", "Going out"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.drop_down_item, typeOfCategory);
        AutoCompleteTextView editTextFilledExposedDropdown = rootView.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown.setAdapter(adapter);

        editTextFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextFilledExposedDropdown.showDropDown();
                Toast.makeText(requireContext(), editTextFilledExposedDropdown.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}