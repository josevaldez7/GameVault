package com.example.gamevault.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gamevault.R;

import java.util.List;
import java.util.Map;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    private final List<String> genres;
    private final Map<String, List<String>> recommendations;
    private final GenreClickListener clickListener;

    public interface GenreClickListener {
        void onGenreClick(String genre);
    }

    public GenreAdapter(List<String> genres, Map<String, List<String>> recommendations, GenreClickListener clickListener) {
        this.genres = genres;
        this.recommendations = recommendations;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_list_item, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.genreName.setText(genre);
        holder.itemView.setOnClickListener(v -> clickListener.onGenreClick(genre));
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class GenreViewHolder extends RecyclerView.ViewHolder {
        private final TextView genreName;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            genreName = itemView.findViewById(R.id.genreName);
        }
    }
}
