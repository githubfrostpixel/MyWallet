package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.R;

import java.util.List;

import entity.Transaction;
import entity.TransactionType;

public class HomeTransactionRecyclerViewAdapter extends RecyclerView.Adapter<HomeTransactionRecyclerViewAdapter.ViewHolder> {
    List<Transaction> transactions;
    Context context;

    public HomeTransactionRecyclerViewAdapter(List<Transaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_transaction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTransactionRecyclerViewAdapter.ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        holder.home_transaction_name.setText(Integer.toString(transaction.getTypeId()));
        holder.home_transaction_date.setText(transaction.getDate().toString());
        holder.home_transaction_value.setText(Integer.toString(transaction.getValue()));
        holder.home_transaction_wallet.setText(Integer.toString(transaction.getWalletId()));

    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView home_transaction_name;
        TextView home_transaction_date;
        TextView home_transaction_value;
        TextView home_transaction_wallet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_transaction_name = itemView.findViewById(R.id.home_transaction_name);
            home_transaction_date = itemView.findViewById(R.id.home_transaction_date);
            home_transaction_value = itemView.findViewById(R.id.home_transaction_value);
            home_transaction_wallet = itemView.findViewById(R.id.home_transaction_wallet);
        }
    }
}