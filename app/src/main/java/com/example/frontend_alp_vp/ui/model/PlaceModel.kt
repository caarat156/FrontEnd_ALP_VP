package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// Wrapper respon dari API (Temanmu menggunakan pola status/success + data)
data class PlaceResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T
)

data class Place(
    @SerializedName("place_id") val id: Int,
    @SerializedName("place_name") val name: String,
    @SerializedName("place_description") val description: String,
    @SerializedName("address") val address: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("rating_avg") val ratingAvg: Double?
)