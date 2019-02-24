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
        String TRAILER_ID = "id";
        String TRAILER_ISO_639 = "iso_639_1";
        String TRAILER_ISO_3166 = "iso_3166_1";
        String TRAILER_KEY = "key";
        String TRAILER_NAME = "name";
        String TRAILER_SITE = "site";
        String TRAILER_SIZE = "size";
        String TRAILER_TYPE = "type";
        List<Trailer> trailers = new ArrayList<>();
        try {
            JSONArray trailersJson = new JSONObject(json).optJSONArray(RESULTS_ARRAY);
            for (int i = 0; i < trailersJson.length(); i++) {
                JSONObject trailerJson = trailersJson.getJSONObject(i);
                trailers.add(new Trailer(
                        trailerJson.optString(TRAILER_ID),
                        trailerJson.optString(TRAILER_ISO_639),
                        trailerJson.optString(TRAILER_ISO_3166),
                        trailerJson.optString(TRAILER_KEY),
                        trailerJson.optString(TRAILER_NAME),
                        trailerJson.optString(TRAILER_SITE),
                        trailerJson.optInt(TRAILER_SIZE),
                        trailerJson.optString(TRAILER_TYPE)
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
