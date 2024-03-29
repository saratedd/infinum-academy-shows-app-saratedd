package com.example.shows_saratedd

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse (
    @SerialName("user") val user: User
)

@Serializable
data class User(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("image_url") val imageUrl: String? // it is nullable, can be null
)