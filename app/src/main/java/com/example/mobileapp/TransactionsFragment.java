package com.example.mobileapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.mobileapp.models.Account;
import com.example.mobileapp.models.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionsFragment extends Fragment {

    private List<Transaction> transactions;
    private ArrayAdapter<String> categoryAdapter;
    private ArrayAdapter<String> accountAdapter;

    private AutoCompleteTextView autoCompleteCategory;
    private AutoCompleteTextView autoCompleteAccounts;
    private TextInputEditText editTextAmount;
    private TextInputEditText editTextMemo;

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

        String[] categories = {"Groceries", "Entertainment", "Transportation", "Travel Expenses", "Utilities", "Other", "Going out"};
        List<String> accounts = new ArrayList<>();

        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, categories);
        accountAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, accounts);

        autoCompleteCategory = rootView.findViewById(R.id.filled_exposed_dropdown);
        autoCompleteCategory.setAdapter(categoryAdapter);

        autoCompleteAccounts = rootView.findViewById(R.id.filled_exposed_dropdown2);

        editTextAmount = rootView.findViewById(R.id.editTextAmount);
        editTextMemo = rootView.findViewById(R.id.editTextMemo);


        getAccountsFromFragment();
        RadioButton radioButton = rootView.findViewById((R.id.radioInflow));


        MaterialButton add = rootView.findViewById(R.id.btnAddTransaction);
        add.setOnClickListener(view -> addTransaction(
                autoCompleteCategory.getText().toString(),
                Double.parseDouble(((EditText) rootView.findViewById(R.id.editTextAmount)).getText().toString()),
                autoCompleteAccounts.getText().toString(),
                ((EditText) rootView.findViewById(R.id.editTextMemo)).getText().toString(),
                radioButton.isChecked()
                ));

        return rootView;
    }

    private void addAmount( double amount,String account) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference collection = db.collection("accounts");


        Query x = collection.whereEqualTo("name",account);


        x.get()
                .addOnSuccessListener(querySnapshots -> {

                    List<DocumentSnapshot> documentSnapshots = querySnapshots.getDocuments();
                    Toast.makeText(getActivity(), documentSnapshots.size(), Toast.LENGTH_SHORT).show();
                    documentSnapshots.forEach((doc)->{

                        DocumentReference documentReference = doc.getReference();
                        documentReference.update("amount", amount);

                        //Toast.makeText(getActivity(), doc.getId(), Toast.LENGTH_SHORT).show();
//                        documentReference.get()
//                                .addOnSuccessListener(documentSnapshot -> {
//                                    try {
//                                        final double currentAmount = documentSnapshot.getDouble("amount");
//                                        documentReference.update("amount", currentAmount + amount);
//                                        //Toast.makeText(getActivity(), documentReference.getId(), Toast.LENGTH_SHORT).show();
//                                    }
//                                    catch (NullPointerException e){
//                                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getActivity(), "couldn't change amount", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    });
                });
    }

    private void addTransaction(String categorie, double amount, String account, String memo, boolean flow) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String currentUserUid = null;
        if (firebaseUser != null)
            currentUserUid = firebaseUser.getUid();
        Map<String, Object> trans = new HashMap<>();
        if (currentUserUid != null) {
            trans.put("id", currentUserUid);
            trans.put("amount", amount);
            trans.put("categorie", categorie);
            trans.put("memo", memo);
            trans.put("account", account);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                trans.put("date", LocalDate.now().toString());
            }
           db.collection("transactions")
                    .add(trans)
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

                            if(flow) {
                                addAmount(amount, account);
                                Toast.makeText(getActivity(), "Transaction added1", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                addAmount(-amount, account);
                                Toast.makeText(getActivity(), "Transaction added2", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Transaction add failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void getAccountsFromFragment() {

        getAccounts(new OnAccountsLoadedListener() {
            @Override
            public void onAccountsLoaded(List<Account> accounts) {
                List<String> names = accounts.stream().map(Account::getName).collect(Collectors.toList());
                accountAdapter.clear();
                accountAdapter.addAll(names);
                accountAdapter.notifyDataSetChanged();
                autoCompleteAccounts.setAdapter(accountAdapter);
            }

            @Override
            public void onAccountsLoadFailed(Exception e) {
                Toast.makeText(getActivity(), "Couldn't load accounts", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface OnAccountsLoadedListener {
        void onAccountsLoaded(List<Account> accounts);

        void onAccountsLoadFailed(Exception e);
    }

    public void getAccounts(OnAccountsLoadedListener onAccountsLoadedListener) {
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
                        onAccountsLoadedListener.onAccountsLoaded(accounts);

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            onAccountsLoadedListener.onAccountsLoadFailed(e);
                            Toast.makeText(getActivity(), "Couldn't load accounts", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        //accounts.forEach(account-> Toast.makeText(getActivity(), account.getName(), Toast.LENGTH_SHORT).show());
        //return accounts;
    }

    @Override
    public void onResume() {
        super.onResume();

        editTextAmount.setText("");
        editTextMemo.setText("");

        autoCompleteCategory.setText("");
        autoCompleteAccounts.setText("");
    }
}