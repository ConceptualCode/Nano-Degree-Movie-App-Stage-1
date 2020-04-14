package com.conceptual.nanodegreemovieapp1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.models.MovieData;
import com.conceptual.nanodegreemovieapp1.models.Result;
import com.conceptual.nanodegreemovieapp1.service.MovieApiService;
import com.conceptual.nanodegreemovieapp1.utils.MovieAPI;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.conceptual.nanodegreemovieapp1.ApiKey.YOUR_API_KEY;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private List<Result> mPopularList = new ArrayList<>();
    private List<Result> mTopRated;
    private TextView txtTryAgain;
    private Button btnTryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.movie_grid_view);
        mProgressBar = findViewById(R.id.progressBar);
        txtTryAgain = findViewById(R.id.text_tryAgain);
        btnTryAgain = findViewById(R.id.button_tryAgain);
        btnTryAgain.setOnClickListener(view -> getPopular());

        getPopular();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(gridLayoutManager);
       setSupportActionBar(toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie, menu);
        return true;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);


        assert cm != null;
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_popular:
                getPopular();
                return true;
            case R.id.menu_top_rated:
                getTopRated();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void getTopRated() {

        if(isOnline()){
        //showProgressBar
        showProgressBar();
        hideTryAgain();
        Call<MovieData> call = MovieAPI.getInstance().create(MovieApiService.class)
                .getTopRatedMovies(YOUR_API_KEY);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(@NonNull Call<MovieData> call, @Nullable Response<MovieData> response) {
                mTopRated = new ArrayList<>();
                assert response != null;
                if (response.isSuccessful()) {
                    MovieData movieData = response.body();
                    assert movieData != null;
                    mTopRated = movieData.getResults();


                    mMovieAdapter = new MovieAdapter(MainActivity.this, mTopRated);
                    //hideProgressBar
                    hideProgressBar();
                    recyclerView.setAdapter(mMovieAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieData> call, @NonNull Throwable t) {
                showTryAgain();
                hideProgressBar();
                Log.d("MainActivity", " failure " + t.getMessage());
                Toast.makeText(MainActivity.this, "No items gotten ", Toast.LENGTH_SHORT).show();
            }
        });}
        else{
           showSnackBar();
        }

    }

    private void getPopular() {
        if (isOnline()){
        showProgressBar();
        hideTryAgain();
        Call<MovieData> call = MovieAPI.getInstance().create(MovieApiService.class).
                getPopularMovies("popularity.desc", YOUR_API_KEY);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(@NonNull Call<MovieData> call, @NonNull Response<MovieData> response) {
                if (response.isSuccessful()) {
                    MovieData movieDataMovies = response.body();
                    assert movieDataMovies != null;
                    mPopularList = movieDataMovies.getResults();
                    Log.d("MainActivity", " Results " + movieDataMovies.getResults().toString());

                    mMovieAdapter = new MovieAdapter(MainActivity.this, mPopularList);
                    hideProgressBar();
                    recyclerView.setAdapter(mMovieAdapter);
                } else {
                    Log.d("MainActivity", " failure " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieData> call, @Nullable Throwable t) {
                hideProgressBar();
                showTryAgain();
                assert t != null;
                Log.d("MainActivity", " failure " + t.getMessage());
                Toast.makeText(MainActivity.this, "No items gotten ", Toast.LENGTH_SHORT).show();
            }
        });}
        else {
            showSnackBar();
        }


    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void showTryAgain() {
        txtTryAgain.setVisibility(View.VISIBLE);
        btnTryAgain.setVisibility(View.VISIBLE);

    }

    private void hideTryAgain() {
        txtTryAgain.setVisibility(View.GONE);
        btnTryAgain.setVisibility(View.GONE);

    }
    private void showSnackBar(){
        Snackbar snackbar = Snackbar
                .make(mProgressBar, "No internet Connection! ", Snackbar.LENGTH_LONG)
                .setAction("Check settings", view -> {
                    Intent settings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    this.startActivity(settings);
                });
        snackbar.setActionTextColor(Color.BLACK);
        View view = snackbar.getView();
        view.setBackgroundColor(ContextCompat.getColor(this
                ,R.color.colorSnackBar));
        snackbar.setText("Check network");
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();

    }

}
