package com.example.gamevault.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamevault.database.entities.Favorites;

import java.util.List;

@Dao
public interface FavoritesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorites... favorite);

    @Delete
    void deleteFavorite(Favorites favorite);

    @Query("SELECT * FROM " + GameVaultDataBase.FAVORITES_TABLE + " ORDER BY favorite_id")
    LiveData<List<Favorites>> getALlFavorites();


    @Query("DELETE from " + GameVaultDataBase.FAVORITES_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + GameVaultDataBase.FAVORITES_TABLE + " WHERE favorite_id == :favorite_id")
    LiveData<Favorites> getFavoriteById(int favorite_id);


    @Update
    void update(Favorites favorite);

}
