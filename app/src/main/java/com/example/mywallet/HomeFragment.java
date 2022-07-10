package com.example.mywallet;

import android.content.SharedPreferences;
import android.graphics.Color;
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


import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

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
import entity.TransactionSumByType;
import entity.TransactionType;
import entity.Wallet;

public class HomeFragment extends Fragment {
    TextView top1, top2, top3, other;
    PieChart pieChart;
    View colorTop1,colorTop2,colorTop3,colorOther;
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
            walletDao.insert(new Wallet("Bank",1012001)); //tinh lai balance cua wallet
            TransactionDao transactionDao = db.transactionDao();
            transactionDao.insert(new Transaction(5,2,31012001,Date.from(LocalDate.of( 2022 , 7 , 1 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Luong thang 7",0));
            transactionDao.insert(new Transaction(1,1,1500000,Date.from(LocalDate.of( 2022 , 7 , 1 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Tien Trong Vi",0));
            transactionDao.insert(new Transaction(6,1,500000,Date.from(LocalDate.of( 2022 , 7 , 2 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Mua do an",1));
            transactionDao.insert(new Transaction(7,2,25000000,Date.from(LocalDate.of( 2022 , 7 , 2 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Mua laptop",1));
            transactionDao.insert(new Transaction(13,2,5000000,Date.from(LocalDate.of( 2022 , 7 , 2 ).atStartOfDay( ZoneId.of( "Asia/Ho_Chi_Minh" )).toInstant()),"Tien Nha",1));

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
        top1 = view.findViewById(R.id.top1);
        top2 = view.findViewById(R.id.top2);
        top3 = view.findViewById(R.id.top3);
        other = view.findViewById(R.id.other);
        colorTop1=view.findViewById(R.id.colorTop1);
        colorTop2=view.findViewById(R.id.colorTop2);
        colorTop3=view.findViewById(R.id.colorTop3);
        colorOther=view.findViewById(R.id.colorOther);
        pieChart = view.findViewById(R.id.piechart);
        setData(db);
//        TRANSACTION
        TransactionDao transactionDao = db.transactionDao();
        List<Transaction> transactions = transactionDao.getAllSortedDate();
        for(Transaction transaction :  transactions){
            if(transaction.getInorout()==1){
                transaction.setValue(transaction.getValue()*-1);
            }
        }
        RecyclerView home_transaction_recycler= view.findViewById(R.id.home_transaction_recycler);
        HomeTransactionRecyclerViewAdapter homeTransactionRecyclerViewAdapter =
                new HomeTransactionRecyclerViewAdapter(transactions,getContext(),db);

        home_transaction_recycler.setAdapter(homeTransactionRecyclerViewAdapter);
        LinearLayoutManager home_transaction_layout_manager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        home_transaction_recycler.setLayoutManager(home_transaction_layout_manager);
    }
    private void setData(AppDatabase db)
    {
        TransactionDao transactionDao=db.transactionDao();
        TransactionTypeDao transactionTypeDao=db.transactionTypeDao();
        List<TransactionSumByType> transactionSumByTypes =  transactionDao.getTransactionSumByType();
        top1.setText("");
        colorTop1.setVisibility(View.INVISIBLE);
        top2.setText("");
        colorTop2.setVisibility(View.INVISIBLE);
        top3.setText("");
        colorTop3.setVisibility(View.INVISIBLE);
        other.setText("");
        colorOther.setVisibility(View.INVISIBLE);
        int count=0;
        int total=0,top1value=0,top2value=0,top3value=0,othervalue=0;
        int top1percent=0,top2percent=0,top3percent=0,otherpercent;
        for(TransactionSumByType transactionSumByType: transactionSumByTypes ){
            count++;
            total+=transactionSumByType.getTotal();
            switch (count){
                case 1:
                    top1.setText(transactionTypeDao.getTransactionTypeByID(transactionSumByType.getTypeID()).get(0).getName());
                    top1value=transactionSumByType.getTotal();
                    colorTop1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    top2.setText(transactionTypeDao.getTransactionTypeByID(transactionSumByType.getTypeID()).get(0).getName());
                    top2value=transactionSumByType.getTotal();
                    colorTop2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    top3.setText(transactionTypeDao.getTransactionTypeByID(transactionSumByType.getTypeID()).get(0).getName());
                    top3value=transactionSumByType.getTotal();
                    colorTop3.setVisibility(View.VISIBLE);
                    break;
                default:
                    othervalue+=transactionSumByType.getTotal();
                    other.setText("Other");
                    colorOther.setVisibility(View.VISIBLE);
                    break;
            }
        }


        for(int i=1;i<=transactionSumByTypes.size();i++){
            switch (i){
                case 1:
                    int i1 = (int) ((top1value*1.0 / total) * 100);
                    top1percent=i1;
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Top1",
                                    top1percent,
                                    Color.parseColor("#FFA726")));
                    break;
                case 2:
                    int i2 = (int) ((top2value*1.0 / total) * 100);
                    top2percent=i2;
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Top2",
                                    top2percent,
                                    Color.parseColor("#66BB6A")));
                    break;
                case 3:
                    int i3 = (int) ((top3value*1.0 / total) * 100);
                    top3percent=i3;
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Top3",
                                    top3percent,
                                    Color.parseColor("#EF5350")));
                    break;
                default:
                    otherpercent=100-top1percent-top2percent-top3percent;
                    pieChart.addPieSlice(
                            new PieModel(
                                    "Other",
                                    otherpercent,
                                    Color.parseColor("#29B6F6")));
                    break;
            }
        }




        // To animate the pie chart
        pieChart.startAnimation();
    }
}
