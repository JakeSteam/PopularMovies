package uk.co.jakelee.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

import uk.co.jakelee.popularmovies.model.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieRecycler = findViewById(R.id.movieRecycler);
        movieRecycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        movieRecycler.setAdapter(new ImageGridAdapter(new ArrayList<Movie>()));
        ApiClient.getApiResponse(this, movieRecycler);
    }

    static List<Movie> getMovieList() {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "a", "2019-01-01", "https://i.imgur.com/JJK2LXK.png", 1.7, "Desc1"));
        movieList.add(new Movie(2, "b", "2019-01-02", "https://i.imgur.com/JJK2LXK.png", 2.7, "Desc2"));
        movieList.add(new Movie(3, "c", "2019-01-03", "https://i.imgur.com/JJK2LXK.png", 3.7, "Desc3"));
        movieList.add(new Movie(4, "d", "2019-01-04", "https://i.imgur.com/JJK2LXK.png", 4.7, "Desc4"));
        return movieList;
    }

    private void launchDetailActivity(int position) {
        //Intent intent = new Intent(this, MovieActivity.class);
        //intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        //startActivity(intent);
    }
}
