package uk.co.jakelee.popularmovies.utilities;

import android.app.Activity;
import android.widget.Toast;

public class ErrorUtil {
    public static void handleApiError(final Activity activity, final String error) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
