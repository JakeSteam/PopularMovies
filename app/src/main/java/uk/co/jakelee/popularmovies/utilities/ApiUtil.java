package uk.co.jakelee.popularmovies.utilities;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import uk.co.jakelee.popularmovies.BuildConfig;
import uk.co.jakelee.popularmovies.R;

public class ApiUtil {
    public static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    public static String getApiUrl(Context context, Boolean usePopular) {
        final String urlPart = usePopular ? "popular" : "top_rated";
        return String.format(context.getString(R.string.api_url), urlPart, BuildConfig.api_key);
    }

    public static String getPosterUrl(Context context,  String suffix) {
        return String.format(context.getString(R.string.poster_url), suffix);
    }
}
