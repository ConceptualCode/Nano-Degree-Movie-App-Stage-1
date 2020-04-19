package com.conceptual.nanodegreemovieapp1.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.conceptual.nanodegreemovieapp1.BuildConfig;
import com.conceptual.nanodegreemovieapp1.database.MovieDatabase;
import com.conceptual.nanodegreemovieapp1.database.RoomMovieDAO;
import com.conceptual.nanodegreemovieapp1.models.MovieData;
import com.conceptual.nanodegreemovieapp1.models.Result;
import com.conceptual.nanodegreemovieapp1.utils.MovieAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivityViewModel extends AndroidViewModel {
        public  MutableLiveData<List<Result>> mPopularMovies;
    public  MutableLiveData<List<Result>> mTopRatedMovies;
    public LiveData<List<Result>> favouriteVideos;
    MovieDatabase mDatabase;
    RoomMovieDAO mMovieDAO;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mDatabase = MovieDatabase.getRoomdb(application);
        mMovieDAO = mDatabase.mMovieDAO();
        getPopularMoviesFromApi();
        favouriteVideos = mMovieDAO.getMovieList();
    }

    public LiveData<List<Result>> getPopular(){
        if (mPopularMovies == null){
            mPopularMovies = new MutableLiveData<>();
            getPopularMoviesFromApi();
        }

        return mPopularMovies;
    }

    public LiveData<List<Result>> getTopRated(){
        if (mTopRatedMovies == null){
            mTopRatedMovies = new MutableLiveData<>();
            getTopRatedFromApi();
        }

        return mTopRatedMovies;
    }


    private void getPopularMoviesFromApi(){
        Call<MovieData> call = MovieAPI.movieAPI.getPopularMovies("popular.desc", BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(@NonNull Call<MovieData> call,@NonNull Response<MovieData> response) {
                if (response.isSuccessful()){

                    assert response.body() != null;
                   mPopularMovies.setValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {

            }
        });
    }

     void getTopRatedFromApi(){
        Call<MovieData> call = MovieAPI.movieAPI.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<MovieData>() {
            @Override
            public void onResponse(@NonNull Call<MovieData> call,@NonNull Response<MovieData> response) {
                if (response.isSuccessful()){
                   mTopRatedMovies.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieData> call, Throwable t) {

            }
        });

    }
}





