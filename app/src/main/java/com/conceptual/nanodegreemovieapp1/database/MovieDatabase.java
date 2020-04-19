package com.conceptual.nanodegreemovieapp1.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.conceptual.nanodegreemovieapp1.models.Result;

@Database(entities = {Result.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

   public static volatile MovieDatabase INSTANCE;

   public static MovieDatabase getRoomdb( final Context context){
       if (INSTANCE == null){
           synchronized (MovieDatabase.class){
               if (INSTANCE == null){
               INSTANCE = Room.databaseBuilder(context.getApplicationContext()
                       ,MovieDatabase.class , "Moviedb").build();
               }

           }
       }
       return INSTANCE;
   }

   public abstract RoomMovieDAO mMovieDAO();
}
