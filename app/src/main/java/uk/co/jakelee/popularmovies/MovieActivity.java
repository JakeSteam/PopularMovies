package uk.co.jakelee.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;
import uk.co.jakelee.popularmovies.utilities.ApiUtil;

public class MovieActivity extends AppCompatActivity {

    public static final String MOVIE = "uk.co.jakelee.popularmovies.movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Movie movie = (Movie) extras.getSerializable(MovieActivity.MOVIE);
            if (movie != null) {
                populateUI(movie);
                setTitle(movie.getTitle());
            }
        } else {
            finish();
        }
    }

    private void populateUI(Movie movie) {
        Picasso.get()
                .load(ApiUtil.getPosterUrl(movie.getPoster()))
                .into((ImageView) findViewById(R.id.movie_poster));
        ((TextView) findViewById(R.id.title)).setText(movie.getTitle());
        ((TextView) findViewById(R.id.synopsis)).setText(movie.getSynopsis());
        ((TextView) findViewById(R.id.release_date)).setText(movie.getReleaseDate());
        ((TextView) findViewById(R.id.average_vote)).setText(movie.getVoteAverage().toString());
    }

    private String toText(List<String> list) {
        return TextUtils.join(", ", list);
    }
}
