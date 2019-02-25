package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.adapters.MovieAdapter;
import uk.co.jakelee.popularmovies.database.MovieRepository;
import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.utilities.ApiUtil;
import uk.co.jakelee.popularmovies.utilities.ErrorUtil;
import uk.co.jakelee.popularmovies.utilities.JsonUtil;

public class MainActivity extends AppCompatActivity {
    private RecyclerView movieRecycler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                getMovies(this, movieRecycler, true);
                break;
            case R.id.action_toprated:
                getMovies(this, movieRecycler, false);
                break;
            case R.id.action_favourites:
                getFavourites(this, movieRecycler);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRecycler = findViewById(R.id.movieRecycler);
        movieRecycler.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        movieRecycler.setLayoutManager(sglm);
        getMovies(this, movieRecycler, true);
    }

    private void getMovies(final Activity activity, final RecyclerView recyclerView, final Boolean popular) {
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
                String responseString = response.body().string();
                List<Movie> movies = JsonUtil.parseMoviesJson(responseString);
                final MovieAdapter movieAdapter = new MovieAdapter(movies);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (popular) {
                            activity.setTitle(activity.getString(R.string.popular_films));
                        } else {
                            activity.setTitle(activity.getString(R.string.top_rated_films));
                        }
                        recyclerView.swapAdapter(movieAdapter, false);
                    }
                });
            }
        });
    }

    private void getFavourites(final Activity activity, final RecyclerView recyclerView) {
        MovieRepository movieRepository = new MovieRepository(this);
        movieRepository.fetchFavourites().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                MovieAdapter movieAdapter = new MovieAdapter(movies);
                recyclerView.swapAdapter(movieAdapter, false);
            }
        });
    }

}
