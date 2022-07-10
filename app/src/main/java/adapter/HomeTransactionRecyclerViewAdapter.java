package adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.mywallet.R;

import java.text.SimpleDateFormat;
import java.util.List;

import dao.AppDatabase;
import dao.TransactionTypeDao;
import dao.WalletDao;
import entity.Transaction;
import entity.TransactionType;

public class HomeTransactionRecyclerViewAdapter extends RecyclerView.Adapter<HomeTransactionRecyclerViewAdapter.ViewHolder> {
    List<Transaction> transactions;
    Context context;
    AppDatabase db;
    public HomeTransactionRecyclerViewAdapter(List<Transaction> transactions, Context context,AppDatabase db) {
        this.transactions = transactions;
        this.context = context;
        this.db=db;
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
        TransactionTypeDao transactionTypeDao = db.transactionTypeDao();
        WalletDao walletDao= db.walletDao();
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        holder.home_transaction_name.setText(transaction.getDescription());
        holder.home_transaction_date.setText(sf.format(transaction.getDate()));
        holder.home_transaction_value.setText(Integer.toString(transaction.getValue()));
        holder.home_transaction_wallet.setText(walletDao.getWalletByID(transaction.getWalletId()).get(0).getName());
        switch (transaction.getTypeId()){
            case 1:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_1);
                break;
            case 2:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_2);
                break;
            case 3:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_3);
                break;
            case 4:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_4);
                break;
            case 5:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_5);
                break;
            case 6:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_6);
                break;
            case 7:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_7);
                break;
            case 8:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_8);
                break;
            case 9:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_9);
                break;
            case 10:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_10);
                break;
            case 11:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_11);
                break;
            case 12:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_12);
                break;
            case 13:
                holder.home_transaction_icon.setImageResource(R.drawable.ic_transaction_type_13);
                break;
        }

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
        ImageView home_transaction_icon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_transaction_name = itemView.findViewById(R.id.home_transaction_name);
            home_transaction_date = itemView.findViewById(R.id.home_transaction_date);
            home_transaction_value = itemView.findViewById(R.id.home_transaction_value);
            home_transaction_wallet = itemView.findViewById(R.id.home_transaction_wallet);
            home_transaction_icon = itemView.findViewById(R.id.home_transaction_icon);
        }
    }
}