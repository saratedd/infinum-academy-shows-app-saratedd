package com.example.shows_saratedd.shows

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class ShowsResponse (
    @SerialName("shows") val shows: Show,
    @SerialName("meta") val meta: Meta
)

@Serializable
data class Meta (
    @SerialName("pagination") val pagination: Pagination
)

@Serializable
data class Pagination (
    @SerialName("count") val count: Int,
    @SerialName("page") val page: Int,
    @SerialName("items") val items: Int,
    @SerialName("pages") val pages: Int
)