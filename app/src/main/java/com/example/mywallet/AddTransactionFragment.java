package com.example.mywallet;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        //Insert transaction
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        EditText amount = view.findViewById(R.id.amount);
        EditText date = view.findViewById(R.id.date);
        EditText desc = view.findViewById(R.id.description);
        Button btn_add_transaction = view.findViewById(R.id.add);

        btn_add_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int typeid = transactionTypeDao.getTransactionTypeByName(types.getSelectedItem().toString()).get(0).getId();
                Wallet walletTmp=walletDao.getWalletByName(wallet.getSelectedItem().toString()).get(0);
                int walletid =
                        walletTmp.getId();
                //Integer.parseInt(String.valueOf(wallet.getSelectedItemId()));
                int value=0;
                try {
                    value = Integer.parseInt(amount.getText().toString().trim());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                String raw_date = date.getText().toString().trim();
                Date trans_date = null;
                try {
                    trans_date = sf.parse(raw_date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String description = desc.getText().toString().trim();
                int inorout = tabLayout.getSelectedTabPosition();
                if(typeid==14 || typeid==15){
                    walletDao.delete(walletTmp);
                    Toast.makeText(getContext(), "Wallet Deleted", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(inorout==0)
                    walletTmp.setBalance(walletTmp.getBalance()+value);
                else
                    walletTmp.setBalance(walletTmp.getBalance()-value);

                walletDao.update(walletTmp);

                if (trans_date == null
                        || amount == null
                        || TextUtils.isEmpty(description)) {
                    Toast.makeText(getContext(), "Please check again", Toast.LENGTH_SHORT).show();
                    return;
                }

                Transaction transaction = new Transaction(typeid, walletid, value, trans_date, description, inorout);
                TransactionDao transactionDao = db.transactionDao();
                transactionDao.insert(transaction);

                Toast.makeText(getContext(), "Add transaction successfully", Toast.LENGTH_SHORT).show();
                wallet.setAdapter(spinnerAdapter1);
                types.setAdapter(spinnerAdapter);
                tabLayout.getTabAt(0);
                amount.setText("");
                date.setText("");
                desc.setText("");
                hideKeyboard(getActivity());

            }

        });



    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
