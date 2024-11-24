package com.example.gamevault.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.gamevault.R;

public class GameVaultViewHolder extends RecyclerView.ViewHolder {
    private final TextView gamevaultViewItem;

    private GameVaultViewHolder(View gamevaultView){
        super(gamevaultView);
        gamevaultViewItem = gamevaultView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind (String text){
        gamevaultViewItem.setText(text);
    }

    static GameVaultViewHolder create(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gamevault_recycler_item, parent, false);
        return new GameVaultViewHolder(view);
    }
}
