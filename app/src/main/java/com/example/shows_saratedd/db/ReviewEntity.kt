package com.example.shows_saratedd.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shows_saratedd.show_details.User

@Entity(tableName = "review")
data class ReviewEntity(
    @ColumnInfo(name = "id") @PrimaryKey val id: String,
    @ColumnInfo(name = "comment") val comment: String,
    @ColumnInfo(name = "rating") val rating: Int,
    @ColumnInfo(name = "show_id") val showId: Int,
    @ColumnInfo(name = "user") val user: User
)

//@Serializable
//data class User(
//    @SerialName("id") val id: String,
//    @SerialName("email") val email: String,
//    @SerialName("image_url") val imageUrl: String?
//)