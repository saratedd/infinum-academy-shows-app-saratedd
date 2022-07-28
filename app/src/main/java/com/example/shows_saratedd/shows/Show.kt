package com.example.shows_saratedd.shows

import androidx.annotation.DrawableRes
import kotlinx.serialization.SerialName

data class Show(
////    we need an id, a name, a desc and an image in the xml file
////    id??? ono iz xmla?
////    val ID: String,
//    val name: String,
//    val description: String,
//    @DrawableRes val imageResourceID: Int
////  annotation @DrawableRes indicates that values of imageResourceId
////  should match resources of type drawable that are added to the app
    @SerialName("id") val id: String,
    @SerialName("average_rating") val average_rating: Float,
    @SerialName("description") val description: String,
    @SerialName("image_url") val image_url: String,
    @SerialName("no_of_reviews") val no_of_reviews: Int,
    @SerialName("title") val title: String
)
