package dao;

import androidx.room.*;

import java.util.List;

import entity.Wallet;

@Dao
public interface WalletDao {
    @Query("SELECT * FROM Wallet")
    List<Wallet> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Wallet... wallets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wallet wallet);

    @Delete
    void delete(Wallet wallet);
}