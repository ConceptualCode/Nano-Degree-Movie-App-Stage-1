package com.conceptual.nanodegreemovieapp1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.conceptual.nanodegreemovieapp1.models.Result;
import com.squareup.picasso.Picasso;

import static com.conceptual.nanodegreemovieapp1.MovieAdapter.API_POSTER_BASE_URL;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView posterImage;
    TextView movieTitle, movieRating, movieOverview, movieReleaseDate;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        posterImage = findViewById(R.id.image_poster);
        movieTitle = findViewById(R.id.text_tittle);
        movieOverview = findViewById(R.id.text_overview);
        movieRating = findViewById(R.id.text_rating);
        movieReleaseDate = findViewById(R.id.text_release_year);
        mToolbar = findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> onBackPressed());

        Intent intentResult = getIntent();
//        intentResult.getSerializableExtra("movie");
        Result result = (Result) intentResult.getSerializableExtra("movie");

        assert result != null;
        String voteAv = result.getVoteAverage() + "/" + "10";
        movieTitle.setText(result.getOriginalTitle());
        movieRating.setText(voteAv);
        movieReleaseDate.setText(result.getReleaseDate());
        movieOverview.setText(result.getOverview());


        String posterUrl = API_POSTER_BASE_URL + "/" +"w185" + "/" + result.getPosterPath();

        Log.d("MainActivity", " Results " + posterUrl);

        Picasso.get()
                .load(posterUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(posterImage);


    }
}
