package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// This matches the "user" object inside your JSON responses
data class UserData(
    @SerializedName("userId")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("phoneNumber") // Standardized to snake_case for backend
    val phoneNumber: String?,

    @SerializedName("profilePicture")
    val profilePhoto: String?
)