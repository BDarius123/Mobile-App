package com.example.mobileapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.adapters.AccountsAdapter;
import com.example.mobileapp.models.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//        mockAccounts.add(new Account("1","Account 1", 1000.0, "Checking"));
//        mockAccounts.add(new Account("2","Account 2", 2500.0, "Savings"));
//        mockAccounts.add(new Account("3","Account 3", 500.0, "Credit Card"));

        mockAccounts = getAccounts();

        recyclerView = rootView.findViewById(R.id.recycler_view_accounts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AccountsAdapter(mockAccounts);

        recyclerView.setAdapter(adapter);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Accounts");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        ImageButton addAccountButton = rootView.findViewById(R.id.fab_add);
        addAccountButton.setOnClickListener(view -> showAddAccountDialog());


//        ImageButton deleteAccountButton = rootView.findViewById(R.id.btn_delete);
//        try {
//            deleteAccountButton.setOnClickListener(view -> {
//                int selectedPosition = adapter.getSelectedPosition();
//                if (selectedPosition != RecyclerView.NO_POSITION) {
//                    deleteConfirmationDialog(selectedPosition);
//                } else {
//                    Toast.makeText(requireContext(), "No account selected", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        catch (NullPointerException e){
//            e.printStackTrace();
//        }

        return rootView;
    }
    private void deleteConfirmationDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Deletion");
        builder.setMessage("Are you sure you want to delete the selected account?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Call the method to delete the selected account
                deleteSelectedAccount(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public List<Account> getAccounts() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String currentUserUid = null;
        if (firebaseUser != null)
            currentUserUid = firebaseUser.getUid();

        List<Account> accounts = new ArrayList<>();


        CollectionReference bankAccountsCollection = FirebaseFirestore.getInstance().collection("bankAccounts");
        Query userAccountsQuery;
        if (currentUserUid != null) {
            userAccountsQuery = bankAccountsCollection.whereEqualTo("user_id", currentUserUid).orderBy("balance");
            userAccountsQuery.get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            String accountId = document.getId();
                            String accountType = document.getString("type");
                            String accountName = document.getString("name");
                            double accountBudget = document.getDouble("balance");

                            accounts.add(new Account(accountId, accountName, accountBudget, accountType));
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Couldn't load accounts", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        return accounts;
    }

    private void deleteSelectedAccount(int position) {
        if (adapter != null) {
            // Call the method to remove the account from your data source
            adapter.removeAccount(position, "");
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
            String type = (String) spinner.getSelectedItem();

            // Add the new account to your data source
            Account newAccount = new Account(null, name, balance, type);
            mockAccounts.add(newAccount);

            addAccount(newAccount);
            // Notify the adapter about the data change
            adapter.notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addAccount(Account account) {
        Map<String, Object> addAccount = new HashMap<>();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String uid = null;
        if (firebaseUser != null) {
            uid = firebaseUser.getUid();
        }
        if (uid != null) {
            addAccount.put("user_id", uid);
            addAccount.put("name", account.getName());
            addAccount.put("balance", account.getBalance());
            addAccount.put("type", account.getType());
            db.collection("bankAccounts")
                    .add(addAccount)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(getActivity(), "Account added", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Account add failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}