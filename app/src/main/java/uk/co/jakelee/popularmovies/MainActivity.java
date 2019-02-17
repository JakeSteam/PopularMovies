package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.utilities.ApiUtil;
import uk.co.jakelee.popularmovies.utilities.JsonUtil;

public class MainActivity extends AppCompatActivity {
    private RecyclerView movieRecycler;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                getApiResponse(this, movieRecycler, true);
                break;
            case R.id.action_toprated:
                getApiResponse(this, movieRecycler, false);
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
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        movieRecycler.setLayoutManager(sglm);
        movieRecycler.setAdapter(new ImageGridAdapter(new ArrayList<Movie>()));
        getApiResponse(this, movieRecycler, true);
    }

    public static void getApiResponse(final Activity activity, final RecyclerView recyclerView, final Boolean popular) {
        Request request = new Request.Builder()
                .url(ApiUtil.getApiUrl(popular))
                .build();
        ApiUtil.httpClient.newCall(request).enqueue(new Callback() {
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
                        List<Movie> movies = JsonUtil.parseMoviesJson(responseString);
                        recyclerView.setAdapter(new ImageGridAdapter(movies));
                    }
                });
            }
        });
    }
}
