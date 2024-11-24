package com.example.gamevault.database;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gamevault.database.entities.GameVault;

import java.util.List;

@Dao
public interface GameVaultDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GameVault gameVault);

    @Query("SELECT * FROM " + GameVaultDataBase.GYM_LOG_TABLE + " ORDER BY date DESC")
    List<GameVault> getAllRecords();

    @Query("SELECT * FROM " + GameVaultDataBase.GYM_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<GameVault> getRecordsByUserId(int loggedInUserId);

    @Query("SELECT * FROM " + GameVaultDataBase.GYM_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    LiveData<List<GameVault>> getRecordsetUserIdLiveData(int loggedInUserId);
}
