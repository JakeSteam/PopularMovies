package uk.co.jakelee.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

@Dao
public interface MovieDao {
    @Insert
    void insertFavourite(Movie movie);

    @Delete
    void deleteFavourite(Movie movie);

    @Query("SELECT * FROM Movie WHERE id =:movieId")
    LiveData<Movie> fetchFavourite(int movieId);

    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> fetchFavourites();
}
