package uk.co.jakelee.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

public class JsonUtil {

    public static List<Movie> parseMoviesJson(String json) {
        String RESULTS_ARRAY = "results";
        String MOVIE_ID = "id";
        String MOVIE_TITLE = "title";
        String MOVIE_RELEASE = "release_date";
        String MOVIE_POSTER = "poster_path";
        String MOVIE_VOTE = "vote_average";
        String MOVIE_OVERVIEW = "overview";
        List<Movie> movies = new ArrayList<>();
        try {
            JSONArray moviesJson = new JSONObject(json).optJSONArray(RESULTS_ARRAY);
            for (int i = 0; i < moviesJson.length(); i++) {
                JSONObject movieJson = moviesJson.getJSONObject(i);
                movies.add(new Movie(
                        movieJson.optInt(MOVIE_ID),
                        movieJson.optString(MOVIE_TITLE),
                        movieJson.optString(MOVIE_RELEASE),
                        movieJson.optString(MOVIE_POSTER),
                        movieJson.optDouble(MOVIE_VOTE),
                        movieJson.optString(MOVIE_OVERVIEW)
                ));
            }
            return movies;
        } catch (JSONException e) {
            return movies;
        }
    }
}
