package uk.co.jakelee.popularmovies.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.model.Review;
import uk.co.jakelee.popularmovies.model.Trailer;

public class JsonUtil {

    public static List<Movie> parseMoviesJson(String json) {
        String RESULTS_ARRAY = "results";
        String MOVIE_IS_ADULT = "adult";
        String MOVIE_ID = "id";
        String MOVIE_TITLE = "title";
        String MOVIE_RELEASE = "release_date";
        String MOVIE_POSTER = "poster_path";
        String MOVIE_VOTE = "vote_average";
        String MOVIE_VOTE_COUNT = "vote_count";
        String MOVIE_OVERVIEW = "overview";
        List<Movie> movies = new ArrayList<>();
        try {
            JSONArray moviesJson = new JSONObject(json).optJSONArray(RESULTS_ARRAY);
            for (int i = 0; i < moviesJson.length(); i++) {
                JSONObject movieJson = moviesJson.getJSONObject(i);
                if (!movieJson.optBoolean(MOVIE_IS_ADULT)) {
                    movies.add(new Movie(
                            movieJson.optInt(MOVIE_ID),
                            movieJson.optString(MOVIE_TITLE),
                            movieJson.optString(MOVIE_RELEASE),
                            movieJson.optString(MOVIE_POSTER),
                            movieJson.optDouble(MOVIE_VOTE),
                            movieJson.optLong(MOVIE_VOTE_COUNT),
                            movieJson.optString(MOVIE_OVERVIEW)
                    ));
                }
            }
            return movies;
        } catch (JSONException e) {
            return movies;
        }
    }

    public static List<Trailer> parseTrailersJson(String json) {
        String RESULTS_ARRAY = "results";
        String TRAILER_KEY = "key";
        String TRAILER_NAME = "name";
        String TRAILER_SITE = "site";
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONArray trailersJson = new JSONObject(json).optJSONArray(RESULTS_ARRAY);
            for (int i = 0; i < trailersJson.length(); i++) {
                JSONObject trailerJson = trailersJson.getJSONObject(i);
                trailers.add(new Trailer(
                        trailerJson.optString(TRAILER_KEY),
                        trailerJson.optString(TRAILER_NAME),
                        trailerJson.optString(TRAILER_SITE)
                ));
            }
            return trailers;
        } catch (JSONException e) {
            return trailers;
        }
    }

    public static List<Review> parseReviewsJson(String json) {
        String RESULTS_ARRAY = "results";
        String REVIEW_ID = "id";
        String REVIEW_AUTHOR = "author";
        String REVIEW_CONTENT = "content";
        String REVIEW_URL = "url";
        List<Review> reviews = new ArrayList<>();
        try {
            JSONArray reviewsJson = new JSONObject(json).optJSONArray(RESULTS_ARRAY);
            for (int i = 0; i < reviewsJson.length(); i++) {
                JSONObject reviewJson = reviewsJson.getJSONObject(i);
                reviews.add(new Review(
                        reviewJson.optString(REVIEW_ID),
                        reviewJson.optString(REVIEW_AUTHOR),
                        reviewJson.optString(REVIEW_CONTENT),
                        reviewJson.optString(REVIEW_URL)
                ));
            }
            return reviews;
        } catch (JSONException e) {
            return reviews;
        }
    }
}
