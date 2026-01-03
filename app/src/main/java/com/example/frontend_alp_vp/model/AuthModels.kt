package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// --- LOGIN MODELS ---
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("token") val token: String?,
    @SerializedName("message") val message: String?
)

// --- REGISTER MODELS ---
data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val phoneNumber: String
)

data class RegisterResponse(
    @SerializedName("message") val message: String?
)