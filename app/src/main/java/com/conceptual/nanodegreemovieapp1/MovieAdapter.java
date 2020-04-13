package com.conceptual.nanodegreemovieapp1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.conceptual.nanodegreemovieapp1.models.Result;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    static final String API_POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private List<Result> mResultList;

    MovieAdapter(Context context, List<Result> movieModelList) {
        mContext = context;
       mLayoutInflater = LayoutInflater.from(mContext);
       mResultList = movieModelList;

    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = mLayoutInflater.inflate(R.layout.movie_recycler_design,parent,false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Result result = mResultList.get(position);

        holder.mCurrentPosition = position;

        String posterUrl = API_POSTER_BASE_URL + "/" +"w185" + "/" + result.getPosterPath();

        Log.d("MainActivity", " Results " + posterUrl);

        Picasso.get()
                .load(posterUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.imagePoster);

    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

      ImageView imagePoster;
        int mCurrentPosition;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imagePoster = itemView.findViewById(R.id.moviePoster);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra("movie", mResultList.get(mCurrentPosition));
                mContext.startActivity(intent);
            });
        }
    }

//    public void setMovieList(List<MovieModel> movieList) {
//        this.mResultList = movieList;
//    }
}
