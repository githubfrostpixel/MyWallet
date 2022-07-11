package com.example.mywallet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import adapter.AddTransactionRecyclerViewAdapter;
import dao.AppDatabase;
import dao.TransactionDao;
import dao.TransactionTypeDao;
import dao.WalletDao;

import entity.Transaction;
import entity.TransactionType;
import entity.Wallet;

public class AddTransactionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_transaction,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "mywallet").allowMainThreadQueries().build();

        TabLayout tabLayout = view.findViewById(R.id.tab);

        //Dropdown Type & Wallet
        Spinner types = view.findViewById(R.id.transaction_type1);
        Spinner wallet = view.findViewById(R.id.wallet1);

        WalletDao walletDao = db.walletDao();
        List<Wallet> wallets = walletDao.getAll();
        ArrayAdapter<String> spinnerAdapter1 =
                new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wallet.setAdapter(spinnerAdapter1);

        for (int i = 0; i < wallets.size(); i++) {
            spinnerAdapter1.addAll(String.valueOf(wallets.get(i).getName()));
        }
        spinnerAdapter1.notifyDataSetChanged();

        TransactionTypeDao transactionTypeDao = db.transactionTypeDao();
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        types.setAdapter(spinnerAdapter);

        //List
        List<TransactionType> transactionTypes = transactionTypeDao.getType(tabLayout.getSelectedTabPosition());
        for (int i = 0; i < transactionTypes.size(); i++) {
            spinnerAdapter.addAll(String.valueOf(transactionTypes.get(i).getName()));
        }

        spinnerAdapter.notifyDataSetChanged();

        //onClick tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ArrayAdapter<String> spinnerAdapter2 =
                        new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, new ArrayList<>());
                spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                types.setAdapter(spinnerAdapter2);

                List<TransactionType> transType = transactionTypeDao.getType(tab.getPosition());
                for (int i = 0; i < transType.size(); i++) {
                    spinnerAdapter2.addAll(String.valueOf(transType.get(i).getName()));
                }

                spinnerAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });









    }
}
