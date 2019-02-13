package uk.co.jakelee.popularmovies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.MovieViewHolder> {

    private List<Movie> movieList;

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
        /*Picasso.get()
                .load(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .resize(400, 400)
                .into(holder.imageView);*/
        holder.imageView.setImageResource(R.drawable.ic_launcher_foreground);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        MovieViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.movieImage);
        }
    }
}