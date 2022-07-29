package com.example.shows_saratedd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.w3c.dom.Comment
import show.Review

class ShowsDetailsViewModel : ViewModel() {

    private val _showReview = MutableLiveData<Review>()
    val showReview: LiveData<Review> = _showReview

    private val _detailsData = MutableLiveData<String>()
    val detailsData: LiveData<String> = _detailsData

    private val _averageRating = MutableLiveData<Float>()
    val averageRating: LiveData<Float> = _averageRating

//    nemam pojma sto bi ovdje trebalo ici


    fun createNewReview(email: String, comment: String, rating: Int) {
//        email = email.substringBefore("@", "")
        _showReview.value = Review(
            email.substringBefore("@", ""),
            comment,
            rating,
            R.drawable.ic_profile_placeholder
        )
    }

    fun updateRating (items: List<Review>) {
        var sum = 0f
        for (item in items) sum += item.rating
        _averageRating.value = sum / items.count()
        _detailsData.value =
            items.count().toString() + " reviews, " + String.format("%.2f", _averageRating.value) + " average"
    }
}

//fun updateRating(): Float {
//    var rating = 0f
//    for (item in items)
//        rating += item.rating
//    return rating / items.count()
//}