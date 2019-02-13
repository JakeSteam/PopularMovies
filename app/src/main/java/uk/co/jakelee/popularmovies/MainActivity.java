package uk.co.jakelee.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

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
        ApiClient.getApiResponse(this, movieRecycler);
    }

    private void launchDetailActivity(int position) {
        //Intent intent = new Intent(this, MovieActivity.class);
        //intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        //startActivity(intent);
    }
}
