package com.conceptual.nanodegreemovieapp1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.conceptual.nanodegreemovieapp1.models.Result;

import java.util.List;

@Dao
public interface RoomMovieDAO  {

    @Insert()
    void insertFavouriteVideo(Result movie);

    @Delete
    void deleteFavouriteVideo(Result movie);



    @Query("SELECT * FROM videos")
    LiveData<List<Result>> getMovieList();




}
