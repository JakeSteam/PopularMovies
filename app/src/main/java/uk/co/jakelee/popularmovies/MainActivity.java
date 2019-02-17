package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieRecycler = findViewById(R.id.movieRecycler);
        movieRecycler.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        movieRecycler.setLayoutManager(sglm);
        movieRecycler.setAdapter(new ImageGridAdapter(new ArrayList<Movie>()));
        getApiResponse(this, movieRecycler, true);
    }

    public static void getApiResponse(final Activity activity, final RecyclerView recyclerView, final Boolean popular) {
        final String urlPart = popular ? "popular" : "top_rated";
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/movie/" + urlPart + "?api_key=" + BuildConfig.api_key)
                .build();
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                final String responseString = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.setTitle(popular ? "Popular Films" : "Top Rated Films");
                        List<Movie> movies = JsonUtils.parseMoviesJson(responseString);
                        recyclerView.setAdapter(new ImageGridAdapter(movies));
                    }
                });
            }
        });
    }
}
