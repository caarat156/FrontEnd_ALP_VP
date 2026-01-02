package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// Generic wrapper for responses that contain UserData
data class UserResponse(
    @SerializedName("data")
    val data: UserData,

    @SerializedName("message")
    val message: String? = null
)