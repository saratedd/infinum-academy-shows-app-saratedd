package com.example.shows_saratedd

import com.example.shows_saratedd.login.LoginRequest
import com.example.shows_saratedd.login.LoginResponse
import com.example.shows_saratedd.register.RegisterRequest
import com.example.shows_saratedd.shows.ShowsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ShowsApiService {

    @POST("/users")
    fun register(@Body request: RegisterRequest) : Call<RegisterResponse>

    @POST("/users/sign_in")
    fun login(@Body request: LoginRequest) : Call<LoginResponse>

    @GET("/shows")
    fun getShowsAlpha() : Call<ShowsResponse>
}