package com.e.mymovies.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.e.mymovie.R;
import com.e.mymovie.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final int IMAGE_SIDE_PX = 250;
    private List<Movie> movies;
    private String url = "http://image.tmdb.org/t/p/w500";
    private MovieClick listener;

    public MovieAdapter(List<Movie> movies, MovieClick listener) {
        this.listener = listener;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView releaseDate;
        TextView title;
        TextView rating;
        ImageView photo;
        Movie movie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            releaseDate = itemView.findViewById(R.id.release_date);
            title = itemView.findViewById(R.id.title_movie);
            rating = itemView.findViewById(R.id.description_movie);
            photo = itemView.findViewById(R.id.poster_movie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(movie);
                }
        });
        }

        public void bind(Movie movie) {
            this.movie = movie;
            releaseDate.setText(movie.getReleaseDate().split("-")[0]);
            title.setText(movie.getTitle());
            rating.setText(String.valueOf(movie.getRating()));
            Picasso.get()
                    .load(url + movie.getPosterPath())
                    .resize(IMAGE_SIDE_PX, IMAGE_SIDE_PX)
                    .centerCrop()
                    .into(photo);

        }

    }
}


