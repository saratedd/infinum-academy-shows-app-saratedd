package com.example.shows_saratedd.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//import com.example.shows_saratedd.show_details.User

@Entity(tableName = "review")
data class ReviewEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "show_id") val showId: Int,
    @ColumnInfo(name = "user_id") val userId: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "user_image_url") val userImageUrl: String?
)
