package com.example.gymlog.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gymlog.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + GymLogDataBase.USER_TABLE + " ORDER BY username")
    List<User> getALlUser();


    @Query("DELETE from " + GymLogDataBase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GymLogDataBase.USER_TABLE + " WHERE username == :username")
    User getUserByUserName(String username);
}
