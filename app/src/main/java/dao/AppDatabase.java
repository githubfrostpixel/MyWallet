package dao;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import entity.Transaction;
import entity.TransactionType;
import entity.Wallet;

@Database(entities = {Wallet.class, Transaction.class,TransactionType.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletDao walletDao();
    public abstract TransactionDao transactionDao();
    public abstract TransactionTypeDao transactionTypeDao();
}