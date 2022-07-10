package dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import entity.TransactionType;
@Dao
public interface TransactionTypeDao {
    @Query("SELECT * FROM `TransactionType` ")
    List<TransactionType> getAll();

    @Query("SELECT * FROM `TransactionType` where type = :type")
    List<TransactionType> getType(int type);

    @Query("SELECT * FROM 'TransactionType' WHERE id =:id")
    List<TransactionType> getTransactionTypeByID(int id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TransactionType... transactionTypes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionType transactionType);

    @Delete
    void delete(TransactionType transactionType);
}
