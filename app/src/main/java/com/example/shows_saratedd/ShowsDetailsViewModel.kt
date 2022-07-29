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

    fun updateDetailsData (itemCount: Int, rating: Float) {
        _detailsData.value =
            itemCount.toString() + " reviews, " + String.format("%.2f", rating) + " average"
    }
}