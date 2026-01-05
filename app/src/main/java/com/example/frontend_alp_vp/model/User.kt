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

    @SerializedName("phone_number")
    val phone_number: String?,

    @SerializedName("profile_photo")
    val profile_photo: String?
)