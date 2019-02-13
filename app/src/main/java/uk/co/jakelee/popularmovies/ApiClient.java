package uk.co.jakelee.popularmovies;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiClient {
    public static void getApiResponse(final Activity activity, final RecyclerView recyclerView) {
        Request request = new Request.Builder()
                .url("http://api.themoviedb.org/3/movie/popular?api_key=" + BuildConfig.api_key)
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
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.setAdapter(new ImageGridAdapter(MainActivity.getMovieList()));
                    }
                });
            }
        });
    }


}
