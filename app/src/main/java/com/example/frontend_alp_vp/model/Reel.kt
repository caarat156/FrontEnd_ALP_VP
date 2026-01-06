package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// 1. Nested User Object
data class ReelUser(
    val username: String,
    @SerializedName("profile_photo")
    val profile_photo: String?
)

// 2. The Reel Object
data class Reel(
    @SerializedName("content_id")
    val id: Int,

    @SerializedName("content_url")
    val content_url: String, // e.g. "public/reelUpload/reels/vid.mp4"

    val caption: String?,

    @SerializedName("users") // Matches Prisma relation
    val user: ReelUser?
)

// 3. Response Wrapper
data class ReelResponse(
    val data: List<Reel>
)