package uk.co.jakelee.popularmovies.utilities;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import uk.co.jakelee.popularmovies.BuildConfig;
import uk.co.jakelee.popularmovies.R;

public class ApiUtil {
    private static final int TIMEOUT_SECONDS = 20;

    public OkHttpClient httpClient(Context context) {
        CacheInterceptor cacheInterceptor = new CacheInterceptor();
        File httpCacheDirectory = new File(context.getCacheDir(), "http-cache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
    }

    public static String getMoviesUrl(Context context, Boolean usePopular) {
        final String urlPart = context.getString(usePopular ? R.string.api_url_part_popular : R.string.api_url_part_top_rated);
        return String.format(context.getString(R.string.api_url_listings), urlPart, BuildConfig.API_KEY);
    }

    public static String getTrailersUrl(Context context, int movieId) {
        return String.format(context.getString(R.string.api_url_trailers), movieId, BuildConfig.API_KEY);
    }

    public static String getReviewsUrl(Context context, int movieId) {
        return String.format(context.getString(R.string.api_url_reviews), movieId, BuildConfig.API_KEY);
    }

    public static String getPosterUrl(Context context,  String suffix) {
        return String.format(context.getString(R.string.poster_url), suffix);
    }

    public class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(1, TimeUnit.HOURS) // 15 minutes cache
                    .build();
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        }
    }
}
