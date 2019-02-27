package uk.co.jakelee.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

public class MovieRepository {

    private String DB_NAME = "movies";

    private MovieDatabase movieDatabase;
    public MovieRepository(Context context) {
        movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).build();
    }

    public void insertFavourite(final Movie movie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                movieDatabase.movieDao().insertFavourite(movie);
                return null;
            }
        }.execute();
    }

    public void deleteFavourite(final Movie movie) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                movieDatabase.movieDao().deleteFavourite(movie);
                return null;
            }
        }.execute();
    }

    public LiveData<Movie> fetchFavourite(int movieId) {
        return movieDatabase.movieDao().fetchFavourite(movieId);
    }

    public LiveData<List<Movie>> fetchFavourites() {
        return movieDatabase.movieDao().fetchFavourites();
    }
}
