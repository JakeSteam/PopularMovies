package uk.co.jakelee.popularmovies.utilities;

import android.app.Activity;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.R;
import uk.co.jakelee.popularmovies.adapters.MovieAdapter;
import uk.co.jakelee.popularmovies.database.MovieRepository;
import uk.co.jakelee.popularmovies.model.Movie;

public class ApiWrapperUtil {
    public static void getMovies(final Activity activity, final RecyclerView recyclerView, final Boolean popular) {
        Request request = new Request.Builder()
                .url(ApiUtil.getMoviesUrl(activity, popular))
                .build();
        new ApiUtil().httpClient(activity).newCall(request).enqueue(new Callback() {
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

    public static void getFavourites(final Activity activity, MovieRepository movieRepo, final RecyclerView recyclerView) {
        movieRepo.fetchFavourites().observe((LifecycleOwner)activity, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                MovieAdapter movieAdapter = new MovieAdapter(movies);
                activity.setTitle(R.string.favourite_films);
                recyclerView.swapAdapter(movieAdapter, false);
            }
        });
    }
}
