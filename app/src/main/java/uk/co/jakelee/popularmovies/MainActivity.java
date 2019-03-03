package uk.co.jakelee.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import uk.co.jakelee.popularmovies.database.MovieRepository;
import uk.co.jakelee.popularmovies.utilities.ApiWrapperUtil;

public class MainActivity extends AppCompatActivity {
    private RecyclerView movieRecycler;
    private int selectedItem = R.id.action_popular;
    private final String SELECTED_KEY = "uk.co.jakelee.popularmovies.selected";
    private MovieRepository movieRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieRepo = new MovieRepository(this);
        movieRecycler = findViewById(R.id.movieRecycler);
        movieRecycler.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        movieRecycler.setLayoutManager(sglm);

        if (savedInstanceState != null) {
            changeTab(savedInstanceState.getInt(SELECTED_KEY, selectedItem));
        } else {
            changeTab(selectedItem);
        }
    }

    private void changeTab(int item) {
        switch (item) {
            case R.id.action_popular:
                ApiWrapperUtil.getMovies(this, movieRecycler, true);
                break;
            case R.id.action_toprated:
                ApiWrapperUtil.getMovies(this, movieRecycler, false);
                break;
            case R.id.action_favourites:
                ApiWrapperUtil.getFavourites(this, movieRepo, movieRecycler);
                break;
        }
        selectedItem = item;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(SELECTED_KEY, selectedItem);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        changeTab(savedInstanceState.getInt(SELECTED_KEY));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        changeTab(item.getItemId());
        return super.onOptionsItemSelected(item);
    }
}
