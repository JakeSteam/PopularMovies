package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.adapters.ReviewAdapter;
import uk.co.jakelee.popularmovies.adapters.TrailerAdapter;
import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.model.Review;
import uk.co.jakelee.popularmovies.model.Trailer;
import uk.co.jakelee.popularmovies.utilities.ApiUtil;
import uk.co.jakelee.popularmovies.utilities.ErrorUtil;
import uk.co.jakelee.popularmovies.utilities.JsonUtil;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE = "uk.co.jakelee.popularmovies.movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = extras.getParcelable(MovieActivity.MOVIE);
            if (movie != null) {
                setTitle(movie.getTitle());
                populateUI(movie);
                getTrailers(this, (RecyclerView)findViewById(R.id.trailerList), movie.getId());
                getReviews(this, (RecyclerView)findViewById(R.id.reviewList), movie.getId());
            }
        } else {
            finish();
        }
    }

    private void populateUI(Movie movie) {
        Picasso.get()
                .load(ApiUtil.getPosterUrl(this, movie.getPoster()))
                .into((ImageView) findViewById(R.id.moviePoster));
        ((TextView) findViewById(R.id.synopsis)).setText(movie.getSynopsis());
        ((TextView) findViewById(R.id.releaseDate)).setText(String.format(
                getString(R.string.release_date),
                movie.getReleaseDate()));
        ((TextView) findViewById(R.id.voteInfo)).setText(String.format(
                getString(R.string.vote_info),
                movie.getVoteAverage(),
                movie.getVoteCount()));
    }

    private void getTrailers(final Activity activity, final RecyclerView recyclerView, final int movieId) {
        Request request = new Request.Builder()
                .url(ApiUtil.getTrailersUrl(activity, movieId))
                .build();
        ApiUtil.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ErrorUtil.handleApiError(activity, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    ErrorUtil.handleApiError(activity, response.message());
                    return;
                }
                final String responseString = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Trailer> trailers = JsonUtil.parseTrailersJson(responseString);
                        recyclerView.swapAdapter(new TrailerAdapter(trailers), false);
                    }
                });
            }
        });
    }

    private void getReviews(final Activity activity, final RecyclerView recyclerView, final int movieId) {
        Request request = new Request.Builder()
                .url(ApiUtil.getReviewsUrl(activity, movieId))
                .build();
        ApiUtil.httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                ErrorUtil.handleApiError(activity, e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (!response.isSuccessful() || response.body() == null) {
                    ErrorUtil.handleApiError(activity, response.message());
                    return;
                }
                final String responseString = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Review> reviews = JsonUtil.parseReviewsJson(responseString);
                        recyclerView.swapAdapter(new ReviewAdapter(reviews), false);
                    }
                });
            }
        });
    }
}
