package uk.co.jakelee.popularmovies.utilities;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import uk.co.jakelee.popularmovies.BuildConfig;

public class ApiUtil {
    public static OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build();

    public static String getApiUrl(Boolean usePopular) {
        final String urlPart = usePopular ? "popular" : "top_rated";
        return "http://api.themoviedb.org/3/movie/" + urlPart + "?api_key=" + BuildConfig.api_key;
    }

    public static String getPosterUrl(String suffix) {
        return "https://image.tmdb.org/t/p/w500" + suffix;
    }
}
