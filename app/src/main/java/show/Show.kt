package show

import androidx.annotation.DrawableRes

data class Show(
//    we need an id, a name, a desc and an image in the xml file
//    id??? ono iz xmla?
    val ID: String,
    val name: String,
    val description: String,
    @DrawableRes val imageResourceID: Int
//  annotation @DrawableRes indicates that values of imageResourceId
//  should match resources of type drawable that are added to the app
)
