package com.example.mobileapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.adapters.AccountsAdapter;
import com.example.mobileapp.models.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {
    private List<Account> mockAccounts = new ArrayList<>();
    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accounts_fragment, container, false);

        mockAccounts.clear();

        mockAccounts.add(new Account("Account 1", 1000.0, "Checking"));
        mockAccounts.add(new Account("Account 2", 2500.0, "Savings"));
        mockAccounts.add(new Account("Account 3", 500.0, "Credit Card"));

        recyclerView = rootView.findViewById(R.id.recycler_view_accounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AccountsAdapter(mockAccounts);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Accounts");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        ImageButton deleteAccountButton = rootView.findViewById(R.id.btn_delete);

//        deleteAccountButton.setOnClickListener(view -> {
//            int selectedPosition = adapter.getSelectedPosition();
//            if (selectedPosition != RecyclerView.NO_POSITION) {
//                deleteConfirmationDialog(selectedPosition);
//            } else {
//                Toast.makeText(requireContext(), "No account selected", Toast.LENGTH_SHORT).show();
//            }
//        });


        ImageButton addAccountButton = rootView.findViewById(R.id.fab_add);
        addAccountButton.setOnClickListener(view -> showAddAccountDialog());


        return rootView;
    }

    private void deleteConfirmationDialog(int position) {
        int selectedPosition = adapter.getSelectedPosition();

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete the selected accounts?");
        builder.setPositiveButton("Yes", (dialog, which) -> deleteSelectedAccount(selectedPosition));
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteSelectedAccount(int position) {
        if (adapter != null) {
            adapter.removeAccount(position);
        }
    }

    private void showAddAccountDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Add Account");

        View view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_account, null);
        builder.setView(view);

        EditText nameEditText = view.findViewById(R.id.edit_text_name);
        EditText balanceEditText = view.findViewById(R.id.edit_text_balance);

        Spinner spinner = view.findViewById(R.id.spinner_account_type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String accountType = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(requireContext(), "Account Type Selected : " + accountType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> accountTypes = new ArrayList<>();
        accountTypes.add("Checking");
        accountTypes.add("Savings");
        accountTypes.add("Credit Card");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, accountTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        builder.setPositiveButton("Confirm", (dialog, which) -> {
            // Retrieve data from the dialog
            String name = nameEditText.getText().toString();
            double balance = Double.parseDouble(balanceEditText.getText().toString());
            String type = nameEditText.getText().toString();

            // Add the new account to your data source
            Account newAccount = new Account(name, balance, type);
            mockAccounts.add(newAccount);

            // Notify the adapter about the data change
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}