package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// Model untuk Review
data class Review(
    @SerializedName("review_id") val id: Int,
    @SerializedName("place_id") val placeId: Int,
    @SerializedName("user_id") val userId: Int,
    val rating: Int,
    val comment: String,
    @SerializedName("created_at") val createdAt: String?
)

// Request untuk membuat review baru
data class CreateReviewRequest(
    @SerializedName("place_id") val placeId: Int,
    @SerializedName("user_id") val userId: Int,
    val rating: Int,
    val comment: String
)