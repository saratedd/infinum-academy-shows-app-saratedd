package com.example.shows_saratedd

import com.example.shows_saratedd.login.LoginRequest
import com.example.shows_saratedd.login.LoginResponse
import com.example.shows_saratedd.login.ProfilePhotoResponse
import com.example.shows_saratedd.register.RegisterRequest
import com.example.shows_saratedd.show_details.ReviewRequest
import com.example.shows_saratedd.show_details.ReviewResponse
import com.example.shows_saratedd.show_details.ReviewsResponse
import com.example.shows_saratedd.shows.Show
import com.example.shows_saratedd.shows.ShowResponse
import com.example.shows_saratedd.shows.ShowsResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest) : Call<RegisterResponse>

    @PUT("/users")
    @Multipart
    fun updateProfilePhoto(@Part image : MultipartBody.Part) : Call<ProfilePhotoResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest) : Call<LoginResponse>

    @GET("/shows")
    fun getShowsAlpha() : Call<ShowsResponse>

    @GET("/shows/{id}")
    fun displayShow(@Path("id") id: String) : Call<ShowResponse>

    @GET("/shows/{show_id}/reviews")
    fun getShowReviews(@Path("show_id") showId: String) : Call<ReviewsResponse>

    @POST("/reviews")
    fun createReview(@Body request: ReviewRequest): Call<ReviewResponse>
}