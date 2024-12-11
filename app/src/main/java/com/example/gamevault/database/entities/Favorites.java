package com.example.gamevault.database.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.gamevault.database.GameVaultDataBase;

import java.util.Objects;

@Entity(
        tableName = GameVaultDataBase.FAVORITES_TABLE,
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = ForeignKey.CASCADE
        )
)
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    private int favorite_id;
    private int user_id;


    public Favorites(int user_id) {
        this.user_id = user_id;
    }

    public int getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(int favorite_id) {
        this.favorite_id = favorite_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorties = (Favorites) o;
        return favorite_id == favorties.favorite_id && user_id == favorties.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(favorite_id, user_id);
    }
}
