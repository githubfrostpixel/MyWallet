package com.example.mywallet;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.HomeTransactionRecyclerViewAdapter;
import dao.AppDatabase;
import dao.TransactionDao;
import dao.TransactionTypeDao;
import dao.WalletDao;
import entity.Transaction;
import entity.TransactionSumByType;
import entity.Wallet;

public class ReportFragment extends Fragment {
    TextView top1, top2, top3, other;
    PieChart pieChart;
    View colorTop1,colorTop2,colorTop3,colorOther;
    int inorout=0,walletid=0;
    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

    Pair<Long, Long> selectedDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppDatabase db = Room.databaseBuilder(getActivity().getApplicationContext(),
                AppDatabase.class, "mywallet").allowMainThreadQueries().build();
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0); // same for minutes and seconds
        selectedDate= new Pair<Long,Long>(new Long(0),today.getTime().getTime());
        mPickDateButton=view.findViewById(R.id.pickDateButton);
        mShowSelectedDateText=view.findViewById(R.id.datePicked);
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getActivity().getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        selectedDate= (Pair<Long, Long>) materialDatePicker.getSelection();
                        selectedDate= new Pair<Long,Long>(selectedDate.first-25200000,selectedDate.second-25200000);
                        Log.i("info",selectedDate.first.toString());
                        mShowSelectedDateText.setText(materialDatePicker.getHeaderText());
                        setData(db);
                        setListData(db, view);
                    }
                });
        setListData(db,view);

        RadioButton radioAll= view.findViewById(R.id.radioAll);
        radioAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    walletid = 0;
                    setData(db);
                    setListData(db, view);
                }
            }
        });

        RadioGroup selectWallet = view.findViewById(R.id.selectWallet);
        WalletDao walletDao= db.walletDao();
        List<Wallet> wallets=walletDao.getAll();
        for(Wallet wallet:wallets){
            RadioButton button = new RadioButton(getContext());
            button.setText(wallet.getName());
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        walletid=wallet.getId();
                        setData(db);
                        setListData(db,view);
                    }
                }
            });
            selectWallet.addView(button);
        }

        RadioButton incomeRadio=view.findViewById(R.id.radioButton);
        RadioButton outcomeRadio=view.findViewById(R.id.radioButton2);
        incomeRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    inorout=0;
                    setData(db);
                    setListData(db,view);
                }
            }
        });
        outcomeRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    inorout=1;
                    setData(db);
                    setListData(db,view);
                }
            }
        });
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


    }
    private void setData(AppDatabase db)
    {
        pieChart.clearChart();
        TransactionDao transactionDao=db.transactionDao();
        TransactionTypeDao transactionTypeDao=db.transactionTypeDao();
        List<TransactionSumByType> transactionSumByTypes=null;
        if(walletid==0)
            transactionSumByTypes =  transactionDao.getTransactionSumByTypeIORange(inorout,new Date(selectedDate.first),new Date(selectedDate.second));
        else
            transactionSumByTypes =  transactionDao.getTransactionSumByTypeIOWalletRange(inorout,walletid,new Date(selectedDate.first),new Date(selectedDate.second));
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
    private void setListData(@NonNull AppDatabase db, View view)
    {
        List<Transaction> transactions=null;
        TransactionDao transactionDao = db.transactionDao();
        if (walletid==0)
            transactions = transactionDao.getAllFilterIOSortedDateRange(inorout,new Date(selectedDate.first),new Date(selectedDate.second));
        else
            transactions = transactionDao.getAllFilterIOWalletSortedDateRange(inorout,walletid,new Date(selectedDate.first),new Date(selectedDate.second));
        for(Transaction transaction :  transactions){
            if(transaction.getInorout()==1){
                transaction.setValue(transaction.getValue()*-1);
            }
        }
        RecyclerView home_transaction_recycler= view.findViewById(R.id.listRecycler);
        HomeTransactionRecyclerViewAdapter homeTransactionRecyclerViewAdapter =
                new HomeTransactionRecyclerViewAdapter(transactions,getContext(),db);

        home_transaction_recycler.setAdapter(homeTransactionRecyclerViewAdapter);
        LinearLayoutManager home_transaction_layout_manager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        home_transaction_recycler.setLayoutManager(home_transaction_layout_manager);

    }
}
