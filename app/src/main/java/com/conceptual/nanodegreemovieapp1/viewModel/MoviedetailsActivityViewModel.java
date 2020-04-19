package com.conceptual.nanodegreemovieapp1.viewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.conceptual.nanodegreemovieapp1.BuildConfig;
import com.conceptual.nanodegreemovieapp1.database.MovieDatabase;
import com.conceptual.nanodegreemovieapp1.database.RoomMovieDAO;
import com.conceptual.nanodegreemovieapp1.models.Result;
import com.conceptual.nanodegreemovieapp1.models.ReviewResult;
import com.conceptual.nanodegreemovieapp1.models.ReviewVideo;
import com.conceptual.nanodegreemovieapp1.models.TrailerResult;
import com.conceptual.nanodegreemovieapp1.models.TrailerVideo;
import com.conceptual.nanodegreemovieapp1.utils.MovieAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MoviedetailsActivityViewModel extends AndroidViewModel {
    MovieDatabase mDatabase;
    RoomMovieDAO mMovieDAO;
    private MutableLiveData<List<TrailerResult>> mTrailerResults;
    private MutableLiveData<List<ReviewResult>> mReviewResults;

    public MoviedetailsActivityViewModel(@NonNull Application application) {
        super(application);

        mDatabase = MovieDatabase.getRoomdb(application);
        mMovieDAO = mDatabase.mMovieDAO();
    }

    public LiveData<List<TrailerResult>> getTrailer(int id){
        if (mTrailerResults == null){
            mTrailerResults = new MutableLiveData<>();
            getTrailerResultsFromApi(id);
        }
        return mTrailerResults;
    }

    public LiveData<List<ReviewResult>> getReviewsw(int id){
        if (mReviewResults == null){
            mReviewResults = new MutableLiveData<>();
            getReviewResultsFromApi(id);
        }
        return mReviewResults;
    }

    private   void getTrailerResultsFromApi (int id){
        Call<TrailerVideo> call = MovieAPI.movieAPI.getTrailers(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<TrailerVideo>() {
            @Override
            public void onResponse(Call<TrailerVideo> call, Response<TrailerVideo> response) {
                if (response.isSuccessful()) {
                    mTrailerResults.setValue(response.body().getResults());

            }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerVideo> call,@NonNull Throwable t) {

            }
        });
    }

    private void getReviewResultsFromApi (int id ){
        Call<ReviewVideo> call = MovieAPI.movieAPI.getReviews(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<ReviewVideo>() {
            @Override
            public void onResponse(@NonNull Call<ReviewVideo> call,@NonNull Response<ReviewVideo> response) {
                    if (response.isSuccessful()){
                        mReviewResults.setValue(response.body().getResults());
                    }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewVideo> call,@NonNull Throwable t) {

            }
        });
    }

    public void saveMovieToFavourite(Result favouriteVideo){
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask(mMovieDAO);
        insertAsyncTask.execute(favouriteVideo);

    }

    public void deleteMovieToFavourite(Result favouriteVideo){
        DeleteAsyncTask deleteAsyncTask = new DeleteAsyncTask(mMovieDAO);
        deleteAsyncTask.execute(favouriteVideo);

    }

    static class InsertAsyncTask extends AsyncTask<Result,Void,Void> {

        private RoomMovieDAO mMovieDAO;

        public InsertAsyncTask(RoomMovieDAO dao){
        this.mMovieDAO = dao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            mMovieDAO.insertFavouriteVideo(results[0]);
            return null;
        }
    }

    static class DeleteAsyncTask extends AsyncTask<Result,Void,Void> {

        private RoomMovieDAO mMovieDAO;

        public DeleteAsyncTask(RoomMovieDAO dao){
            this.mMovieDAO = dao;
        }

        @Override
        protected Void doInBackground(Result... results) {
            mMovieDAO.deleteFavouriteVideo(results[0]);
            return null;
        }
    }
}
