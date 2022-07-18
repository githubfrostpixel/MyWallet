package com.example.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import java.util.List;

import adapter.WalletViewAdapter;
import dao.AppDatabase;
import dao.WalletDao;
import entity.Wallet;

public class WalletFragment extends Fragment {
    private WalletViewAdapter adapter;
//    private List<Wallet> listWallet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallet,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listViewWallet = view.findViewById(R.id.listViewWallet);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
        AppDatabase.class, "mywallet").allowMainThreadQueries().build();

        WalletDao walletDao = db.walletDao();
        List<Wallet> wallets = walletDao.getAll();

        adapter = new WalletViewAdapter(this, wallets);
        listViewWallet.setAdapter(adapter);
        int totalBalance=0;
        TextView totalBalanceWallet = view.findViewById(R.id.totalBalanceWallet);
        for(Wallet wallet:wallets)
            totalBalance+=wallet.getBalance();
        totalBalanceWallet.setText("Total Balance: "+ totalBalance+ "₫");
        TextView addWallet = view.findViewById(R.id.addWallet);
        addWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(this,AddWallet.class);
            }
        });
    }


}
