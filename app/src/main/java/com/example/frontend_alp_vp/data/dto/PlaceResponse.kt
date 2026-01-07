package com.example.frontend_alp_vp.data.dto

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    val success: Boolean,
    val data: List<PlaceDTO>
)

data class PlaceDTO(
    @SerializedName("place_id") val id: Int,
    @SerializedName("place_name") val name: String,
    @SerializedName("place_description") val description: String,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("address") val address: String,
    @SerializedName("rating_avg") val rating: Double?
)