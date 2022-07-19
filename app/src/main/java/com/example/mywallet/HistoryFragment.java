package com.example.mywallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

import adapter.HomeTransactionRecyclerViewAdapter;
import adapter.HomeWalletRecyclerViewAdapter;
import dao.AppDatabase;
import dao.TransactionDao;
import dao.WalletDao;
import entity.Transaction;
import entity.Wallet;


public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "mywallet").allowMainThreadQueries().build();
        TransactionDao transactionDao = db.transactionDao();
        List<Transaction> transactions = transactionDao.getAllSortedDate();
        for(Transaction transaction :  transactions){
            if(transaction.getInorout()==1){
                transaction.setValue(transaction.getValue()*-1);
            }
        }
        RecyclerView home_transaction_recycler= view.findViewById(R.id.historyRecycleView);
        HomeTransactionRecyclerViewAdapter homeTransactionRecyclerViewAdapter =
                new HomeTransactionRecyclerViewAdapter(transactions,getContext(),db);

        home_transaction_recycler.setAdapter(homeTransactionRecyclerViewAdapter);
        LinearLayoutManager home_transaction_layout_manager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        home_transaction_recycler.setLayoutManager(home_transaction_layout_manager);
    }
}
