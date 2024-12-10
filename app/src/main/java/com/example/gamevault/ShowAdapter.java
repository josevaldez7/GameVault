package com.example.gamevault;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowViewHolder> {

    private List<Show> showList;

    public ShowAdapter(List<Show> showList) {
        this.showList = showList;
    }

    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, parent, false);
        return new ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShowViewHolder holder, int position) {
        Show show = showList.get(position);
        holder.name.setText(show.getName());
        holder.rating.setText("Rating: " + show.getRating().getAverage());

        // Load the poster using Glide
        if (show.getPoster() != null && !show.getPoster().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(show.getPoster())
                    .into(holder.showPoster);
        } else {
            holder.showPoster.setImageResource(0);
        }
    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class ShowViewHolder extends RecyclerView.ViewHolder {

        TextView name, rating;
        ImageView showPoster;

        public ShowViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.showName);
            rating = itemView.findViewById(R.id.showRating);
            showPoster = itemView.findViewById(R.id.showPoster);
        }
    }
}
