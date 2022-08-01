package com.example.shows_saratedd.show_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.R

class ShowsDetailsViewModel : ViewModel() {

    private val _showReview = MutableLiveData<Review>()
    val showReview: LiveData<Review> = _showReview

    private val _detailsData = MutableLiveData<String>()
    val detailsData: LiveData<String> = _detailsData

    private val _averageRating = MutableLiveData<Float>()
    val averageRating: LiveData<Float> = _averageRating


    fun createNewReview(email: String, comment: String, rating: Int) {
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
