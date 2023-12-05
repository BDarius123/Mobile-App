package com.example.mobileapp.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.Account;

import java.text.DecimalFormat;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>{
    private List<Account> accounts;

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
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public static class AccountViewHolder extends RecyclerView.ViewHolder {
        private TextView accountNameTextView;
        private TextView balanceTextView;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            accountNameTextView = itemView.findViewById(R.id.accountNameTextView);
            balanceTextView = itemView.findViewById(R.id.balanceTextView);
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
