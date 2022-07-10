package com.example.mywallet;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.time.LocalDate;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;

import adapter.HomeTransactionRecyclerViewAdapter;
import adapter.HomeWalletRecyclerViewAdapter;
import dao.AppDatabase;
import dao.TransactionDao;
import dao.TransactionTypeDao;
import dao.WalletDao;
import entity.Transaction;
import entity.TransactionType;
import entity.Wallet;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView totalBalanceTextView= view.findViewById(R.id.totalBalanceTextView);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
        AppDatabase.class, "mywallet").allowMainThreadQueries().build();
//        FIRST TIME?
        boolean mboolean = false;

        SharedPreferences settings = getContext().getSharedPreferences("SETTING", 0);
        mboolean = settings.getBoolean("FIRST_RUN", false);
        if (!mboolean) {
            // do the thing for the first time
            settings = getContext().getSharedPreferences("SETTING", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("FIRST_RUN", true);
            editor.commit();
            TransactionTypeDao transactionTypeDao = db.transactionTypeDao();
            // 0 - income, 1 - outcome
            transactionTypeDao.insert(new TransactionType("Deposit",0));
            transactionTypeDao.insert(new TransactionType("Withdraw",1));
            transactionTypeDao.insert(new TransactionType("Transfer",0));
            transactionTypeDao.insert(new TransactionType("Transfer",1));
            transactionTypeDao.insert(new TransactionType("Salary",0));
            transactionTypeDao.insert(new TransactionType("Food & Drink",1));
            transactionTypeDao.insert(new TransactionType("Shopping",1));
            transactionTypeDao.insert(new TransactionType("Housing",1));
            transactionTypeDao.insert(new TransactionType("Transportation",1));
            transactionTypeDao.insert(new TransactionType("Vehical",1));
            transactionTypeDao.insert(new TransactionType("Life & Entertainment",1));
            transactionTypeDao.insert(new TransactionType("Communication, PC",1));
            transactionTypeDao.insert(new TransactionType("Financial expenses",1));

//            DUMMY DATA FOR DEMO ONLY
            WalletDao walletDao = db.walletDao();
            walletDao.insert(new Wallet("Wallet",1000000));
            walletDao.insert(new Wallet("Bank",31012001)); //tinh lai balance cua wallet
            TransactionDao transactionDao = db.transactionDao();
            transactionDao.insert(new Transaction(1,2,31012001,Date.from(LocalDate.of( 2022 , 7 , 1 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Luong thang 7"));
            transactionDao.insert(new Transaction(1,1,1500000,Date.from(LocalDate.of( 2022 , 7 , 1 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Tien Trong Vi"));
            transactionDao.insert(new Transaction(4,1,500000,Date.from(LocalDate.of( 2022 , 7 , 2 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Mua do an"));
        }
//        WALLET
        WalletDao walletDao=db.walletDao();
        List<Wallet> wallets=walletDao.getAll();
        int totalBalance=0;
        for(Wallet wallet:wallets)
            totalBalance+=wallet.getBalance();
        totalBalanceTextView.setText("Total Balance: "+ totalBalance+ "₫");
        RecyclerView home_wallet_recycler = view.findViewById(R.id.home_wallet_recycler);
        HomeWalletRecyclerViewAdapter home_wallet_recycler_adapter = new HomeWalletRecyclerViewAdapter(wallets,getContext());
        home_wallet_recycler.setAdapter(home_wallet_recycler_adapter);
        LinearLayoutManager home_wallet_layout_manager= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false);
        home_wallet_recycler.setLayoutManager(home_wallet_layout_manager);
//        REPORT

//        TRANSACTION
        TransactionDao transactionDao = db.transactionDao();
        List<Transaction> transactions = transactionDao.getAll();

        RecyclerView home_transaction_recycler= view.findViewById(R.id.home_transaction_recycler);
        HomeTransactionRecyclerViewAdapter homeTransactionRecyclerViewAdapter =
                new HomeTransactionRecyclerViewAdapter(transactions,getContext());

        home_transaction_recycler.setAdapter(homeTransactionRecyclerViewAdapter);
        LinearLayoutManager home_transaction_layout_manager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        home_transaction_recycler.setLayoutManager(home_transaction_layout_manager);
    }
}
