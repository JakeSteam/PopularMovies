package uk.co.jakelee.popularmovies.utilities;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import uk.co.jakelee.popularmovies.BuildConfig;
import uk.co.jakelee.popularmovies.R;

public class ApiUtil {
    private static final int TIMEOUT_SECONDS = 20;
    public static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    public static String getApiUrl(Context context, Boolean usePopular) {
        final String urlPart = context.getString(usePopular ? R.string.api_url_part_popular : R.string.api_url_part_top_rated);
        return String.format(context.getString(R.string.api_url), urlPart, BuildConfig.API_KEY);
    }

    public static String getPosterUrl(Context context,  String suffix) {
        return String.format(context.getString(R.string.poster_url), suffix);
    }
}
