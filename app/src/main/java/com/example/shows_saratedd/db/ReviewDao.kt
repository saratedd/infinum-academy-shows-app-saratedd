package com.example.shows_saratedd.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ReviewDao {

//- Retrieve all the reviews
    @Query("SELECT * FROM review WHERE show_id = :showId")
    fun getAllReviews(showId: Int): LiveData<List<ReviewEntity>>

//- Retrieve a single review
    @Query("SELECT * FROM review WHERE id IS :reviewId")
    fun getReview(reviewId: String): LiveData<ReviewEntity>

//- Create a new review
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNewReview(review: ReviewEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllReviews(reviews: List<ReviewEntity>)

}

