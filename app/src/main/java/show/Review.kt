package show

import androidx.annotation.DrawableRes

data class Review(
    val name: String,
    val comment: String,
    val rating: Int,
    @DrawableRes val imageResourceID: Int
)