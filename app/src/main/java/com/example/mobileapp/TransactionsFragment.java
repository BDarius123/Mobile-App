package com.example.mobileapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


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
        //adapter = new TransactionsAdapter(transactions);
        //recyclerView.setAdapter(adapter);

        // Handle adding a new transaction (this is just a basic example)

        Button btnAddTransaction = rootView.findViewById(R.id.btnAddTransaction);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input (replace with your UI elements)
                String category = "Travel Expenses"; // Example category
                String account = "Savings Account"; // Example account
                double amount = 50.0; // Example amount
                Date date = new Date(); // Example date
                String memo = "This is an optional memo"; // Example memo

                // Create a new transaction
                Transaction newTransaction = new Transaction(category, account, date, amount, memo);

                // Add the transaction to the list
                transactions.add(newTransaction);

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged();
            }
        });

        return rootView;
    }
}