package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mywallet.R;

import java.util.ArrayList;
import java.util.List;

import entity.Wallet;

public class HomeWalletRecyclerViewAdapter extends RecyclerView.Adapter<HomeWalletRecyclerViewAdapter.ViewHolder> {
    List<Wallet> wallets;
    Context context;

    public HomeWalletRecyclerViewAdapter(List<Wallet> wallets, Context context) {
        this.wallets = wallets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_wallet_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeWalletRecyclerViewAdapter.ViewHolder holder, int position) {
        Wallet wallet=wallets.get(position);
        holder.home_wallet_item_name.setText(wallet.getName());
        holder.home_wallet_item_balance.setText(wallet.getBalance()+"₫");
    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView home_wallet_item_name;
        TextView home_wallet_item_balance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            home_wallet_item_name=itemView.findViewById(R.id.home_wallet_item_name);
            home_wallet_item_balance=itemView.findViewById(R.id.home_wallet_item_balance);
        }
    }
}
