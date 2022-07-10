package dao;

import androidx.room.*;

import java.util.List;

import entity.Transaction;

@Dao
public interface TransactionDao{
    @Query("SELECT * FROM `Transaction`")
    List<Transaction> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Transaction... transactions);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);


}