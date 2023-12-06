package com.example.mobileapp.adapters;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.Account;

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
            removeAccount(position);
        });
    }

    public void removeAccount(int position) {
        accounts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, accounts.size());

        selectedPosition = RecyclerView.NO_POSITION;
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
