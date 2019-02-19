package uk.co.jakelee.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.utilities.ApiUtil;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.MovieViewHolder> {

    private final List<Movie> movieList;

    ImageGridAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_griditem, parent, false);

        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        Picasso.get()
                .load(ApiUtil.getPosterUrl(holder.imageView.getContext(), movie.getPoster()))
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(MovieActivity.MOVIE, movie);

                Intent intent = new Intent(v.getContext(), MovieActivity.class);
                intent.putExtras(bundle);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;

        MovieViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.movieImage);
        }
    }
}