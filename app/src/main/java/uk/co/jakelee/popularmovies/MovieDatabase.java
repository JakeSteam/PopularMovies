package uk.co.jakelee.popularmovies;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import uk.co.jakelee.popularmovies.database.MovieDao;
import uk.co.jakelee.popularmovies.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();
}
