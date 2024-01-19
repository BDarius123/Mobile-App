package com.example.mobileapp;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.example.mobileapp.adapters.BudgetAdapter;
import com.example.mobileapp.models.Budget;
import com.example.mobileapp.models.Transaction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
        View rootView = inflater.inflate(R.layout.budget_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recycler_view_budgets);


        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Budget");
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.white));

        if (recyclerView != null) {

            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

            recyclerView.setHasFixedSize(true);

            List<Transaction> manualTransactions = new ArrayList<>();
//            manualTransactions.add(new Transaction("Groceries", "Account 1", "2020-12-02", 50.0, "Buy groceries"));
//            manualTransactions.add(new Transaction("Groceries", "Account 2", "2020-05-22", 25.0, "Movie night"));
//            manualTransactions.add(new Transaction("Transportation", "Account 3","2020-06-013", 30.0, "Gas for car"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manualTransactions = getTransactions();
            }

            adapter = new BudgetAdapter(manualTransactions);

            adapter.setOriginalList(manualTransactions);

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
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return rootView;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Transaction> getTransactions(){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        List<Transaction> budgetList = new ArrayList<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String currentUserUid = null;
        if (firebaseUser != null)
            currentUserUid = firebaseUser.getUid();

        CollectionReference budgetCollection = FirebaseFirestore.getInstance().collection("transactions");
        Query query = budgetCollection.whereEqualTo("id",currentUserUid);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for(QueryDocumentSnapshot document:queryDocumentSnapshots){
                        budgetList.add(
                                new Transaction(
                                        document.getId(),
                                        document.getString("categorie"),
                                        document.getString("account"),
                                        document.getString("date"),
                                        document.getString("memo"),
                                        document.getDouble("amount")
                                )
                        );
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Couldn't load budgets", Toast.LENGTH_SHORT).show();

                    }
                });

        return budgetList;
    }
}