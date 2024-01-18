package com.example.mobileapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mobileapp.adapters.BudgetAdapter;
import com.example.mobileapp.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BudgetFragment extends Fragment {

    private RecyclerView recyclerView;

    private SearchView searchView;

    private BudgetAdapter adapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.budget_fragment, container, false);

        // Find the RecyclerView in the inflated layout
        recyclerView = rootView.findViewById(R.id.recycler_view_budgets);


        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Budget");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        // Check if recyclerView is not null before using it
        if (recyclerView != null) {
            // Set the layout manager
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            // Set other properties as needed
            recyclerView.setHasFixedSize(true);

            // Create a few manual transactions
            List<Transaction> manualTransactions = new ArrayList<>();
            manualTransactions.add(new Transaction("Groceries", "Account 1", "2020-12-02", 50.0, "Buy groceries"));
            manualTransactions.add(new Transaction("Entertainment", "Account 2", "2020-05-22", 25.0, "Movie night"));
            manualTransactions.add(new Transaction("Transportation", "Account 3","2020-06-013", 30.0, "Gas for car"));

            // Create the adapter with the manual transactions
            adapter = new BudgetAdapter(manualTransactions);

            adapter.setOriginalList(manualTransactions);

            // Set the adapter to the RecyclerView
            recyclerView.setAdapter(adapter);
        }

        searchView = rootView.findViewById(R.id.searchViewBudget);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // When the user presses the search button
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // When the user changes the text in the search box
                adapter.getFilter().filter(newText); // Filter the adapter based on the new text
                return false;
            }
        });

        return rootView;
    }
}