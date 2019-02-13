package uk.co.jakelee.popularmovies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

public class JsonUtils {
    public static List<Movie> parseMoviesJson(String json) {
        try {
            JSONArray moviesJson = new JSONObject(json).optJSONArray("results");
            List<Movie> movies = new ArrayList<>();
            for (int i=0; i < moviesJson.length(); i++) {
                JSONObject movieJson = moviesJson.getJSONObject(i);
                movies.add(new Movie(
                        movieJson.optInt("id"),
                        movieJson.optString("title"),
                        movieJson.optString("release_date"),
                        movieJson.optString("poster_path"),
                        movieJson.optDouble("vote_average"),
                        movieJson.optString("overview")
                ));
            }
            return movies;
        } catch (JSONException e) {
            return null;
        }
    }
}
