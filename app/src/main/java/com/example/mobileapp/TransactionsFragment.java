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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.adapters.TransactionsAdapter;
import com.example.mobileapp.models.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TransactionsFragment extends Fragment {

    private List<Transaction> transactions;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> accountAdapter;


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

        // Initialize your adapters (assuming you have lists of categories and accounts)
        String[] categories = {"Groceries", "Entertainment", "Transportation", "Travel Expenses", "Utilities", "Other", "Going out"};
        List<String> accounts = getAccountsFromFragment(); // Implement this method

        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        accountAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, accounts);

        AutoCompleteTextView autoCompleteCategory = rootView.findViewById(R.id.filled_exposed_dropdown);
        autoCompleteCategory.setAdapter(categoryAdapter);

        AutoCompleteTextView autoCompleteAccounts = rootView.findViewById(R.id.filled_exposed_dropdown2);
        autoCompleteAccounts.setAdapter(accountAdapter);

        // Rest of your code for handling button click, adding transactions, etc.

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
    private List<String> getAccountsFromFragment() {
        // Add logic to retrieve accounts from AccountsFragment
        return Arrays.asList("Account1", "Account2", "Account3");
    }
}