package com.example.mobileapp;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mobileapp.adapters.AccountsAdapter;
import com.example.mobileapp.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {
    private List<Account> mockAccounts = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accounts_fragment, container, false);

        mockAccounts.clear();

        mockAccounts.add(new Account("Account 1", 1000.0));
        mockAccounts.add(new Account("Account 2", 2500.0));
        mockAccounts.add(new Account("Account 3", 500.0));

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_accounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AccountsAdapter(mockAccounts));

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Accounts");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));


        return rootView;
    }
}