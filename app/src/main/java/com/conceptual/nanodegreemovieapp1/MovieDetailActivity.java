package com.conceptual.nanodegreemovieapp1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.adapters.ReviewAdapter;
import com.conceptual.nanodegreemovieapp1.adapters.TrailerAdapter;
import com.conceptual.nanodegreemovieapp1.models.Result;
import com.conceptual.nanodegreemovieapp1.viewModel.MoviedetailsActivityViewModel;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import static com.conceptual.nanodegreemovieapp1.MovieAdapter.API_POSTER_BASE_URL;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView posterImage;
    TextView movieTitle, movieRating, movieOverview, movieReleaseDate;
    Toolbar mToolbar;
    MaterialButton mbtnAddToFavourite, mBtnRemoveFromFavorite;
    MoviedetailsActivityViewModel mViewModel;
    RecyclerView mTrailerReyclerView, mReviewRecyclerView;


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
        mTrailerReyclerView = findViewById(R.id.trailerRecyclerView);
        mReviewRecyclerView = findViewById(R.id.reviewRecyclerView);
        mbtnAddToFavourite = findViewById(R.id.favoriteButton);
        mBtnRemoveFromFavorite = findViewById(R.id.removeButton);

        mViewModel = ViewModelProviders.of(this).get(MoviedetailsActivityViewModel.class);


        if (!readButtonState("favourite")){
            mbtnAddToFavourite.setVisibility(View.VISIBLE);
            mBtnRemoveFromFavorite.setVisibility(View.GONE);
        }else
        {
            mbtnAddToFavourite.setVisibility(View.GONE);
            mBtnRemoveFromFavorite.setVisibility(View.VISIBLE);
        }

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


            mReviewRecyclerView.setLayoutManager( new LinearLayoutManager(this));
            mTrailerReyclerView.setLayoutManager( new LinearLayoutManager(this));

            mViewModel.getReviewsw(result.getId()).observe(this, reviewResults -> {

                mReviewRecyclerView.setAdapter(new ReviewAdapter(reviewResults, MovieDetailActivity.this));
            });

            mViewModel.getTrailer(result.getId()).observe(this, trailerResults -> {
                mTrailerReyclerView.setAdapter(new TrailerAdapter(trailerResults, MovieDetailActivity.this));
            });

            mbtnAddToFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isFavourite = readButtonState("favourite");
                    if (!isFavourite){
                        mViewModel.saveMovieToFavourite(result);
                        isFavourite = true;
                        saveButtonState(isFavourite, "favourite");
                        mbtnAddToFavourite.setEnabled(false);
                    }

                }
            });


            mBtnRemoveFromFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isFavourite = readButtonState("favourite");
                    if (isFavourite){
                        mViewModel.deleteMovieToFavourite(result);
                        isFavourite = false;
                        saveButtonState(isFavourite, "favourite");
                        mbtnAddToFavourite.setEnabled(false);
                    }
                }
            });


    }

    public void saveButtonState(boolean isChecked, String key) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(" com.conceptual.nanodegreemovieapp1", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isChecked);
        editor.apply();
    }

    public boolean readButtonState(String key) {
        SharedPreferences preferences = getSharedPreferences(" com.conceptual.nanodegreemovieapp1", MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(key, false);
        return isChecked;
    }
}
