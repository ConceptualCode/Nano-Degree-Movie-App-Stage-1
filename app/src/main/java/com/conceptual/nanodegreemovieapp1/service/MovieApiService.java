package com.conceptual.nanodegreemovieapp1.service;

import com.conceptual.nanodegreemovieapp1.models.MovieData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("movie/popular")
    Call<MovieData> getPopularMovies(@Query("sort_by") String sort_by,
                                     @Query("api_key") String api_key );

    @GET("movie/top_rated")
    Call<MovieData> getTopRatedMovies(@Query("api_key") String apiKey);



}
