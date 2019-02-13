package uk.co.jakelee.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MovieActivity extends AppCompatActivity {

    public static final String TITLE = "uk.co.jakelee.popularmovies.title";
    public static final String RELEASE_DATE = "uk.co.jakelee.popularmovies.releasedate";
    public static final String POSTER = "uk.co.jakelee.popularmovies.poster";
    public static final String VOTE_AVERAGE = "uk.co.jakelee.popularmovies.voteaverage";
    public static final String SYNOPSIS = "uk.co.jakelee.popularmovies.synopsis";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_detail);

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            closeOnError();
            return;
        }

        Picasso.with(this)
                .load(sandwich.getImage())
                .into((ImageView) findViewById(R.id.image_iv));
        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ((TextView) findViewById(R.id.description_tv)).setText(sandwich.getDescription());
        ((TextView) findViewById(R.id.ingredients_tv)).setText(toText(sandwich.getIngredients()));
        ((TextView) findViewById(R.id.origin_tv)).setText(sandwich.getPlaceOfOrigin());
        ((TextView) findViewById(R.id.also_known_tv)).setText(toText(sandwich.getAlsoKnownAs()));
    }

    private String toText(List<String> list) {
        return TextUtils.join(", ", list);*/
    }
}
