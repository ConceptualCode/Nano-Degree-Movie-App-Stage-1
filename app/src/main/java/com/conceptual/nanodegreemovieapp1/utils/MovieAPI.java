package com.conceptual.nanodegreemovieapp1.utils;

import com.conceptual.nanodegreemovieapp1.service.MovieApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class MovieAPI {

    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit mRetrofit;

    public static Retrofit getInstance(){
        if (mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }


    public static MovieApiService movieAPI = getInstance().create(MovieApiService.class);


}
