package com.example.shows_saratedd.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShowDao {

//    store a list of shows in the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllShows(shows: List<ShowEntity>)

//    retrieve a list of shows from the database
//    method that returns all shows
    @Query("SELECT * FROM shows")
    fun getAllShows(): LiveData<List<ShowEntity>>

//    returns only a single show by its ID
    @Query("SELECT * FROM shows WHERE id IS :showId")
    fun getShow(showId: String): LiveData<ShowEntity>

}
