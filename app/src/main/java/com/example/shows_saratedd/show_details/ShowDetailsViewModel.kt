package com.example.shows_saratedd.show_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shows_saratedd.ApiModule
import com.example.shows_saratedd.RegisterResponse
import com.example.shows_saratedd.register.RegisterRequest
import com.example.shows_saratedd.shows.Show
import com.example.shows_saratedd.shows.ShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsViewModel : ViewModel() {

    private val _showReview = MutableLiveData<Review>()
    val showReview: LiveData<Review> = _showReview

    private val _detailsData = MutableLiveData<String>()
    val detailsData: LiveData<String> = _detailsData

    private val _averageRating = MutableLiveData<Float>()
    val averageRating: LiveData<Float> = _averageRating

    private val reviewsResponseLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    private val reviewsResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val showResponseLiveData: MutableLiveData<Show> by lazy {
        MutableLiveData<Show>()
    }

    private val showResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val createReviewResultLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val createReviewResponseLiveData: MutableLiveData<Review> by lazy {
        MutableLiveData<Review>()
    }

    fun getReviewsResultLiveData(): LiveData<Boolean> {
        return reviewsResultLiveData
    }

    fun getShowResultLiveData(): LiveData<Boolean> {
        return showResultLiveData
    }

    fun getReviewsResponseLiveData(): LiveData<List<Review>> {
        return reviewsResponseLiveData
    }

    fun getShowResponseLiveData(): LiveData<Show> {
        return showResponseLiveData
    }

    fun getCreateReviewResultLiveData(): LiveData<Boolean> {
        return createReviewResultLiveData
    }

    fun getCreateReviewResponseLiveData(): LiveData<Review> {
        return createReviewResponseLiveData
    }

//    fun createNewReview(id: String, comment: String, rating: Int, showId: String, user: User) {
//        _showReview.value = Review(
//            id,
//            comment,
//            rating,
//            showId.toInt(),
////            email.substringBefore("@", ""),
////            R.drawable.ic_profile_placeholder
//            user
//        // fali user
//        )
//    }

    fun updateRating(items: List<Review>) {
        var sum = 0f
        for (item in items) sum += item.rating
        _averageRating.value = sum / items.count()
        _detailsData.value =
            items.count().toString() + " reviews, " + String.format("%.2f", _averageRating.value) + " average"
    }

    fun loadReviews(showId: String) {
        ApiModule.retrofit.getShowReviews(showId)
            .enqueue(object : Callback<ReviewsResponse> {
                override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                    reviewsResultLiveData.value = response.isSuccessful
                    reviewsResponseLiveData.value = response.body()?.reviews
                }

                override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) { }
            })
    }

    fun loadShowDetails(showId: String) {
        ApiModule.retrofit.displayShow(showId)
            .enqueue(object : Callback<ShowResponse> {
                override fun onResponse(call: Call<ShowResponse>, response: Response<ShowResponse>) {
                    showResultLiveData.value = response.isSuccessful
                    showResponseLiveData.value = response.body()?.show
                }

                override fun onFailure(call: Call<ShowResponse>, t: Throwable) {
                    showResultLiveData.value = true
                }

            })
    }

    fun onSubmitButtonClicked(showId: String, rating: Int, comment: String) {
        val reviewRequest = ReviewRequest(
            rating = rating,
            comment = comment,
            showId = showId.toInt()
        )

        ApiModule.retrofit.createReview(reviewRequest)
            .enqueue(object: Callback<ReviewResponse> {
                override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                    createReviewResultLiveData.value = response.isSuccessful
                    createReviewResponseLiveData.value = response.body()?.review
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    createReviewResultLiveData.value = false
                }

            })
    }
}
