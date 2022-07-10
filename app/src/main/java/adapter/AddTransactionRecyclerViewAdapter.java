package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    List<TransactionType> transactionTypes;
    List<Wallet> wallets;
    Context context;

    public AddTransactionRecyclerViewAdapter(List<TransactionType> transactionTypes, List<Wallet> wallets, Context context) {
        this.transactionTypes = transactionTypes;
        this.wallets = wallets;
        this.context = context;
    }


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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transaction_type = itemView.findViewById(R.id.transaction_type1);
            wallet = itemView.findViewById(R.id.wallet1);
        }
    }

    private class OnItemClickListener1 implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }
}
