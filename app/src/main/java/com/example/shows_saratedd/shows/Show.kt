package com.example.shows_saratedd.shows

//import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Show(

//    val ID: String,
//    val name: String,
//    val description: String,
//    @DrawableRes val imageResourceID: Int

    @SerialName("id") val id: String,
    @SerialName("average_rating") val average_rating: Float,
    @SerialName("description") val description: String,
    @SerialName("image_url") val image_url: String,
    @SerialName("no_of_reviews") val no_of_reviews: Int,
    @SerialName("title") val title: String
)
