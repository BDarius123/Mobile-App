package com.example.mobileapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.R;
import com.example.mobileapp.models.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> implements Filterable  {
    private List<Transaction> budgetItemList;
    private List<Transaction> budgetItemListFull;

    public BudgetAdapter(List<Transaction> budgetItemList) {
        this.budgetItemList = budgetItemList;
    }

    public static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        TextView accountTextView;
        TextView dateTextView;
        TextView memoTextView;
        TextView amountTextView;

        public BudgetViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize your views
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            accountTextView = itemView.findViewById(R.id.accountTextView);
            dateTextView = itemView.findViewById(R.id.memoDateView);
            memoTextView = itemView.findViewById(R.id.memoTextView);
            amountTextView = itemView.findViewById(R.id.memoAmountView);
        }


    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout and create a new ViewHolder
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_transactions, parent, false);

        return new BudgetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        // Bind data to views based on the item position
        Transaction currentItem = budgetItemList.get(position);

        holder.categoryTextView.setText("Category: " + currentItem.getCategory());
        holder.accountTextView.setText("Account: " + currentItem.getAccount());
        holder.dateTextView.setText("Date: " + currentItem.getDate());
        holder.memoTextView.setText("Memo: " + currentItem.getMemo());
        holder.amountTextView.setText("Amount: " + currentItem.getAmount());


    }

    @Override
    public int getItemCount() {
        // Return the size of your dataset (number of items)
        return budgetItemList.size();
    }

    public void updateData(List<Transaction> transactions) {
        this.budgetItemList = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return budgetFilter;
    }

    private final Filter budgetFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transaction> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(budgetItemListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Transaction transaction : budgetItemListFull) {
                    if (transaction.getCategory().toLowerCase().contains(filterPattern)) {
                        filteredList.add(transaction);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            budgetItemList.clear();
            budgetItemList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public void setOriginalList(List<Transaction> originalList) {
        this.budgetItemListFull = new ArrayList<>(originalList);
    }
}
