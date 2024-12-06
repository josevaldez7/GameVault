package com.example.gamevault.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamevault.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM " + GameVaultDataBase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getALlUsers();


    @Query("DELETE from " + GameVaultDataBase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GameVaultDataBase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * FROM " + GameVaultDataBase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    @Update
    void update(User user);

}
