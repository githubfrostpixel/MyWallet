package dao;

import androidx.room.*;

import java.util.List;

import entity.Wallet;

@Dao
public interface WalletDao {
    @Query("SELECT * FROM Wallet")
    List<Wallet> getAll();
    @Query("SELECT * From Wallet WHERE id=:id")
    List<Wallet> getWalletByID(int id);
    @Query("SELECT * From Wallet WHERE name=:name")
    List<Wallet> getWalletByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Wallet... wallets);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wallet wallet);
    @Update
    void update(Wallet wallet);
    @Delete
    void delete(Wallet wallet);
}