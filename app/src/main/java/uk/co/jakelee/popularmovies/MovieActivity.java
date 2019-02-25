package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import uk.co.jakelee.popularmovies.database.MovieRepository;
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

    private void populateUI(final Movie movie) {
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
        MovieRepository movieRepository = new MovieRepository(this);
        movieRepository.fetchFavourite(movie.getId()).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie savedMovie) {
                Boolean isFavourite = savedMovie != null;
                updateFavouriteButton(movie, isFavourite);
                findViewById(R.id.favouriteButton).setVisibility(View.VISIBLE);
            }
        });
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
                String responseString = response.body().string();
                List<Trailer> trailers = JsonUtil.parseTrailersJson(responseString);
                final TrailerAdapter trailerAdapter = new TrailerAdapter(trailers);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (trailerAdapter.getItemCount() > 0) {
                            recyclerView.swapAdapter(trailerAdapter, false);
                            findViewById(R.id.trailerGroup).setVisibility(View.VISIBLE);
                        }
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
                String responseString = response.body().string();
                List<Review> reviews = JsonUtil.parseReviewsJson(responseString);
                final ReviewAdapter reviewAdapter = new ReviewAdapter(reviews);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (reviewAdapter.getItemCount() > 0) {
                            recyclerView.swapAdapter(reviewAdapter, false);
                            findViewById(R.id.reviewGroup).setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private void updateFavouriteButton(Movie movie, Boolean isInFavourites) {
        Button favouriteButton = findViewById(R.id.favouriteButton);
        favouriteButton.setOnClickListener(favouriteClickListener(movie, isInFavourites));
        if (isInFavourites) {
            favouriteButton.setText(getString(R.string.remove_from_favourites));
        } else {
            favouriteButton.setText(getString(R.string.add_to_favourites));
        }
    }

    private View.OnClickListener favouriteClickListener(final Movie movie, final Boolean isInFavourites) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieRepository movieRepository = new MovieRepository(v.getContext());
                if (isInFavourites) {
                    movieRepository.deleteFavourite(movie);
                } else {
                    movieRepository.insertFavourite(movie);
                }
                updateFavouriteButton(movie, !isInFavourites);
            }
        };
    }
}
