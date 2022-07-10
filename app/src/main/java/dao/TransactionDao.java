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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Transaction... transactions);
    @Query("SELECT TypeID as TypeID,sum(value) as Total from `Transaction` GROUP BY TypeID ORDER BY Total DESC")
    List<TransactionSumByType> getTransactionSumByType();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);


}