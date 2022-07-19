package com.example.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import org.w3c.dom.Text;

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
    TextView walletName;
    TextView balance;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
        AppDatabase.class, "mywallet").allowMainThreadQueries().build();
        walletName=view.findViewById(R.id.editwalletname);
        balance=view.findViewById(R.id.editBalance);

        Button button = view.findViewById(R.id.AddWalletButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletDao walletDao = db.walletDao();
                String name =  walletName.getText().toString();
                if(name==""){
                    Toast.makeText(getContext(),"Please input name",Toast.LENGTH_SHORT);
                }
                else {
                    String balances = balance.getText().toString();
                    if (balances == "") balances = "0";
                    walletDao.insert(new Wallet(name, Integer.parseInt(balances)));
                    Toast.makeText(getContext(), "Wallet Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
