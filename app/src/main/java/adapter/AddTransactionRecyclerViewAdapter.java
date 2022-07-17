package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.R;

import java.util.List;

import entity.Transaction;
import entity.TransactionType;
import entity.Wallet;

public class AddTransactionRecyclerViewAdapter extends RecyclerView.Adapter<AddTransactionRecyclerViewAdapter.ViewHolder> {

    @NonNull
    @Override
    public AddTransactionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_add_transaction, parent, false);
        return new AddTransactionRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddTransactionRecyclerViewAdapter.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Spinner transaction_type;
        Spinner wallet;
        EditText amount;
        EditText date;
        EditText description;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_type = itemView.findViewById(R.id.transaction_type1);
            wallet = itemView.findViewById(R.id.wallet1);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);
        }
    }

}
