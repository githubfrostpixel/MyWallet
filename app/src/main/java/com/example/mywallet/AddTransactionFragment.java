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

        TransactionTypeDao transactionTypeDao = db.transactionTypeDao();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<TransactionType> transactionTypes = transactionTypeDao.getType(tab.getPosition());
                ArrayAdapter<CharSequence> adapter =
                        new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, transactionTypes);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
                types.setAdapter(adapter);


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
