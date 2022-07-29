package com.example.shows_saratedd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.w3c.dom.Comment
import show.Review

class ShowsDetailsViewModel : ViewModel() {

    private val _showReview = MutableLiveData<Review>()
    val showReview: LiveData<Review> = _showReview

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
}