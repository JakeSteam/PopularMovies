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
import uk.co.jakelee.popularmovies.model.Movie;
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
                populateUI(movie);
                setTitle(movie.getTitle());
            }
        } else {
            finish();
        }
    }

    private void populateUI(Movie movie) {
        Picasso.get()
                .load(ApiUtil.getPosterUrl(this, movie.getPoster()))
                .into((ImageView) findViewById(R.id.movie_poster));
        ((TextView) findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.synopsis)).setText(movie.getSynopsis());
        ((TextView) findViewById(R.id.release_date)).setText(movie.getReleaseDate());
        ((TextView) findViewById(R.id.average_vote)).setText(String.format(
                getString(R.string.average_vote_format),
                movie.getVoteAverage()));
    }

    private void getTrailer(final Activity activity, final RecyclerView recyclerView, final Boolean popular) {
        Request request = new Request.Builder()
                .url(ApiUtil.getMoviesUrl(activity, popular))
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
                        if (popular) {
                            activity.setTitle(activity.getString(R.string.popular_films));
                        } else {
                            activity.setTitle(activity.getString(R.string.top_rated_films));
                        }
                        List<Movie> movies = JsonUtil.parseMoviesJson(responseString);
                        recyclerView.swapAdapter(new ImageGridAdapter(movies), false);
                    }
                });
            }
        });
    }
}
