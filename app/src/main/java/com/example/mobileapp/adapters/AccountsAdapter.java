package com.example.mobileapp.adapters;


import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.AccountsFragment;
import com.example.mobileapp.LoginActivity;
import com.example.mobileapp.NavBarActivity;
import com.example.mobileapp.R;
import com.example.mobileapp.models.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DecimalFormat;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder> {
    private final List<Account> accounts;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public AccountsAdapter(List<Account> accounts) {
        this.accounts = accounts;
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_accounts, parent, false);

        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.bind(account);

        holder.btnDelete.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition();
            removeAccount(position, account.getName());
        });
    }

    public void removeAccount(int position, String name) {
        accounts.remove(position);
        removeFromFirebase(name);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, accounts.size());

        selectedPosition = RecyclerView.NO_POSITION;
    }

    public void removeFromFirebase(String name) {
        CollectionReference bankAccounts = FirebaseFirestore.getInstance().collection("bankAccounts");
        Query query = bankAccounts.whereEqualTo("name", name);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        DocumentReference documentReference = documentSnapshot.getReference();

                        documentReference.delete();
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(getName().getContext(), "Account deleted", Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(getName().getContext(), "Account couldn't be deleted", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
                    }
                });
    }

    public String getNameAtPosition(int position) {
        return accounts.get(position).getName();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private final TextView accountNameTextView;
        private final TextView balanceTextView;
        private final ImageButton btnDelete;
        private TextView typeTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.accountNameTextView);
            balanceTextView = itemView.findViewById(R.id.balanceTextView);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void bind(Account account) {
            accountNameTextView.setText(account.getName());
            String formattedBalance = formatCurrency(account.getBalance());
            balanceTextView.setText(formattedBalance);
        }

        private String formatCurrency(double balance) {
            DecimalFormat currencyFormatter = new DecimalFormat("$#,##0.00");
            return currencyFormatter.format(balance);
        }
    }

}
