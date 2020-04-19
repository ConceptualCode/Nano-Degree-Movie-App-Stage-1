package com.conceptual.nanodegreemovieapp1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.models.Result;
import com.conceptual.nanodegreemovieapp1.viewModel.MainActivityViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private List<Result> mPopularList = new ArrayList<>();
    private List<Result> mTopRated;
    private TextView txtTryAgain;
    private Button btnTryAgain;
    private MainActivityViewModel mViewModel;

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
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        showProgressBar();

        getPopular();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2, LinearLayoutManager.VERTICAL
                , false);
        recyclerView.setLayoutManager(gridLayoutManager);
       setSupportActionBar(toolbar);


    }

    private void getPopular() {
    if (isOnline()){
        mViewModel.getPopular().observe(this, popularMoviesList->{
            hideProgressBar();
            recyclerView.setAdapter(new MovieAdapter(MainActivity.this, popularMoviesList));
        });
    }else
        showSnackBar();


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
                showProgressBar();
                getPopular();
                return true;
            case R.id.menu_top_rated:
                showProgressBar();
                getTopRated();
                return true;
            case R.id.menu_favourite:
                getFavourite();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void getFavourite() {
        mViewModel.favouriteVideos.observe(this, favouriteList ->{
            recyclerView.setAdapter(new MovieAdapter(MainActivity.this, favouriteList));
        });
    }

    private void getTopRated() {
        if (isOnline()){
            mViewModel.getTopRated().observe(this, popularMoviesList->{
                hideProgressBar();
                recyclerView.setAdapter(new MovieAdapter(MainActivity.this, popularMoviesList));
            });
        }
        else
            showSnackBar();

    }



    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
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
