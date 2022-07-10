package dao;

import androidx.room.*;

import java.util.List;

import entity.Transaction;
import entity.TransactionSumByType;

@Dao
public interface TransactionDao{
    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();
    @Query("SELECT * FROM `Transaction` ORDER BY date DESC")
    List<Transaction> getAllSortedDate();
    @Query("SELECT * FROM `Transaction` WHERE inorout=:inorout ORDER BY date DESC")
    List<Transaction> getAllFilterIOSortedDate(int inorout);
    @Query("SELECT * FROM `Transaction` WHERE inorout=:inorout AND walletId=:walletId ORDER BY date DESC")
    List<Transaction> getAllFilterIOWalletSortedDate(int inorout,int walletId);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Transaction... transactions);
    @Query("SELECT TypeID as TypeID,sum(value) as Total from `Transaction` GROUP BY TypeID ORDER BY Total DESC")
    List<TransactionSumByType> getTransactionSumByType();

    @Query("SELECT TypeID as TypeID,sum(value) as Total from (SELECT* from `Transaction` where inorout=:inorout) GROUP BY TypeID ORDER BY Total DESC")
    List<TransactionSumByType> getTransactionSumByTypeIO(int inorout);
    @Query("SELECT TypeID as TypeID,sum(value) as Total from (SELECT* from `Transaction` where inorout=:inorout and walletId=:walletid) GROUP BY TypeID ORDER BY Total DESC")
    List<TransactionSumByType> getTransactionSumByTypeIOWallet(int inorout,int walletid);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);


}