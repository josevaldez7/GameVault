package com.example.gamevault;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private List<Movie> movieList;

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.title.setText(movie.getTitle());
        holder.year.setText(movie.getYear());
        holder.plot.setText(movie.getPlot());

        // Add Metascore display
        if (movie.getMetascore() != null && !movie.getMetascore().isEmpty()) {
            holder.metascore.setText("Metascore: " + movie.getMetascore());
            holder.metascore.setVisibility(View.VISIBLE);
        } else {
            holder.metascore.setVisibility(View.GONE);
        }

        Glide.with(holder.itemView.getContext()).load(movie.getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void updateMovies(List<Movie> newMovies) {
        this.movieList = newMovies;  // Update the movie list
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title, year, plot, metascore;
        ImageView poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movieTitle);
            year = itemView.findViewById(R.id.movieYear);
            plot = itemView.findViewById(R.id.moviePlot);
            poster = itemView.findViewById(R.id.moviePoster);

            // Add metascore TextView
            metascore = itemView.findViewById(R.id.movieMetascore);
        }
    }
}