package com.example.shows_saratedd.show_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.R
import com.example.shows_saratedd.shows.ShowsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowsDetailsViewModel : ViewModel() {

    private val _showReview = MutableLiveData<Review>()
    val showReview: LiveData<Review> = _showReview

    private val _detailsData = MutableLiveData<String>()
    val detailsData: LiveData<String> = _detailsData

    private val _averageRating = MutableLiveData<Float>()
    val averageRating: LiveData<Float> = _averageRating

    private val responseLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    private val reviewsResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getReviewsResultLiveData(): LiveData<Boolean> {
        return reviewsResultLiveData
    }


    fun createNewReview(id: String, comment: String, rating: Int, showId: String, user: User) {
        _showReview.value = Review(
            id,
            comment,
            rating,
            showId.toInt(),
//            email.substringBefore("@", ""),
//            R.drawable.ic_profile_placeholder
            user
        // fali user
        )
    }

    fun getResponseLiveData(): LiveData<List<Review>> {
        return responseLiveData
    }

    fun updateRating(items: List<Review>) {
        var sum = 0f
        for (item in items) sum += item.rating
        _averageRating.value = sum / items.count()
        _detailsData.value =
            items.count().toString() + " reviews, " + String.format("%.2f", _averageRating.value) + " average"
    }

    fun loadReviews(showId: String) {
        ApiModule.retrofit.getShowReviews(showId)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                    reviewsResultLiveData.value = response.isSuccessful
                    responseLiveData.value = response.body()?.reviews
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d("shows", "onfailure: " + t.message)
                }
            })
    }
}
