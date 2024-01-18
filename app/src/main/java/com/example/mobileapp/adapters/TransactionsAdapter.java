package com.example.mobileapp.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.Transaction;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder> {


    @NonNull
    @Override
    public TransactionsAdapter.TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.TransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    static class TransactionViewHolder extends RecyclerView.ViewHolder {
//        private final TextInputLayout textInputLayoutCategory;
//        private final TextInputEditText editTextCategory;

        private final TextInputLayout textInputLayoutAccount;
        private final TextInputEditText editTextAccount;

        private final TextInputLayout textInputLayoutDate;
        private final TextInputEditText editTextDate;

        private final TextInputLayout textInputLayoutMemo;
        private final TextInputEditText editTextMemo;

        private final MaterialButton btnAddTransaction;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
//            textInputLayoutCategory = itemView.findViewById(R.id.textInputLayoutCategory);
//            editTextCategory = itemView.findViewById(R.id.editTextCategory);

            textInputLayoutAccount = itemView.findViewById(R.id.textInputLayoutAccount);
            editTextAccount = itemView.findViewById(R.id.editTextAccount);

            textInputLayoutDate = itemView.findViewById(R.id.textInputLayoutDate);
            editTextDate = itemView.findViewById(R.id.editTextDate);

            textInputLayoutMemo = itemView.findViewById(R.id.textInputLayoutMemo);
            editTextMemo = itemView.findViewById(R.id.editTextMemo);

            btnAddTransaction = itemView.findViewById(R.id.btnAddTransaction);
        }

        public void bind(Transaction transaction) {

            //editTextCategory.setText(transaction.getCategory());
            editTextAccount.setText(transaction.getAccount());
            editTextDate.setText(transaction.getDateAsString());
            editTextMemo.setText(transaction.getMemo());

            if (transaction.getMemo() != null && !transaction.getMemo().isEmpty()) {
                textInputLayoutMemo.setVisibility(View.VISIBLE);
            } else {
                textInputLayoutMemo.setVisibility(View.GONE);
            }
        }
    }
}
