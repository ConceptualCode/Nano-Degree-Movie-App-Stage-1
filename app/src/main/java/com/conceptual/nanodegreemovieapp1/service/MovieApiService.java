package com.conceptual.nanodegreemovieapp1.service;

import com.conceptual.nanodegreemovieapp1.models.MovieData;
import com.conceptual.nanodegreemovieapp1.models.ReviewVideo;
import com.conceptual.nanodegreemovieapp1.models.TrailerVideo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieData> getPopularMovies(@Query("sort_by") String sort_by,
                                     @Query("api_key") String api_key );

    @GET("movie/top_rated")
    Call<MovieData> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    Call<ReviewVideo> getReviews(@Path("id") int id,
                                @Query("api_key") String api_key );

    @GET("movie/{id}/videos")
    Call<TrailerVideo> getTrailers(@Path("id") int id,
                                   @Query("api_key") String api_key );




}
