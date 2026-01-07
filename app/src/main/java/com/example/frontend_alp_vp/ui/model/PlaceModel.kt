package com.example.frontend_alp_vp.ui.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: List<PlaceData>
)

data class PlaceData(
    @SerializedName("place_id") val place_id: Int,
    @SerializedName("place_name") val place_name: String,
    @SerializedName("place_description") val place_description: String,
    @SerializedName("address") val address: String,
    @SerializedName("image_url") val image_url: String?,
    @SerializedName("rating_avg") val rating_avg: Double,
    @SerializedName("review_count") val review_count: Int,
    @SerializedName("category_name") val category_name: String
)