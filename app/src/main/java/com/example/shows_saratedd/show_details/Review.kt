package com.example.shows_saratedd.show_details

//import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(
//    val name: String,
//    val comment: String,
//    val rating: Int,
//    @DrawableRes val imageResourceID: Int
    @SerialName("id") val id: String,
    @SerialName("comment") val comment: String,
    @SerialName("rating") val rating: Int,
    @SerialName("show_id") val showId: Int,
    @SerialName("user") val user: User
)

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("image_url") val imageUrl: String?
)

@Serializable
data class ReviewResponse(
    @SerialName("review") val review: Review
)