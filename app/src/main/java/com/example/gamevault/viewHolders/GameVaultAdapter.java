package com.example.gamevault.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.gamevault.database.entities.GameVault;

public class GameVaultAdapter extends ListAdapter<GameVault, GameVaultViewHolder> {
    public GameVaultAdapter(@NonNull DiffUtil.ItemCallback<GameVault> diffCallback){
        super(diffCallback);
    }

    @NonNull
    @Override
    public GameVaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return GameVaultViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GameVaultViewHolder holder, int position) {
        GameVault current = getItem(position);
        holder.bind(current.toString());
    }

    public static class gamevaultDiff extends DiffUtil.ItemCallback<GameVault> {
        @Override
        public boolean areItemsTheSame(@NonNull GameVault oldItem, @NonNull GameVault newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GameVault oldItem, @NonNull GameVault newItem) {
            return oldItem.equals(newItem);
        }
    }

}
