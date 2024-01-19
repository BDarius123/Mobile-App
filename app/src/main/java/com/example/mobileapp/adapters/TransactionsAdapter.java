package com.example.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.Transaction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {

    private List<Transaction> transactionList;
    private ArrayAdapter<String> accountAdapter;
    private ArrayAdapter<String> categoryAdapter;

    public TransactionsAdapter(List<Transaction> transactionList, ArrayAdapter<String> accountAdapter, ArrayAdapter<String> categoryAdapter) {
        this.transactionList = transactionList;
        this.accountAdapter = accountAdapter;
        this.categoryAdapter = categoryAdapter;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.transactions_fragment, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        Transaction currentItem = transactionList.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final AutoCompleteTextView autoCompleteCategory;
        private final AutoCompleteTextView autoCompleteAccount;
        private final TextInputLayout textInputLayoutDate;
        private final TextInputLayout textInputLayoutMemo;
        private final TextInputEditText editTextMemo;
        private final MaterialButton btnAddTransaction;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            autoCompleteCategory = itemView.findViewById(R.id.filled_exposed_dropdown);
            autoCompleteAccount = itemView.findViewById(R.id.filled_exposed_dropdown2);
            textInputLayoutDate = itemView.findViewById(R.id.textInputLayoutDate);

            textInputLayoutMemo = itemView.findViewById(R.id.textInputLayoutMemo);
            editTextMemo = itemView.findViewById(R.id.editTextMemo);
            btnAddTransaction = itemView.findViewById(R.id.btnAddTransaction);

        }

        public void bind(Transaction transaction) {
            autoCompleteCategory.setText(transaction.getCategory(), false);
            autoCompleteAccount.setText(transaction.getAccount(), false);

            editTextMemo.setText(transaction.getMemo());

            if (transaction.getMemo() != null && !transaction.getMemo().isEmpty()) {
                textInputLayoutMemo.setVisibility(View.VISIBLE);
            } else {
                textInputLayoutMemo.setVisibility(View.GONE);
            }
        }
    }
}
