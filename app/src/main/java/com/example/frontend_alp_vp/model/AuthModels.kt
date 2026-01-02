package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// --- LOGIN ---
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val message: String,
    val token: String,
    val user: UserData?
)

// --- REGISTER ---
data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String
)

data class RegisterResponse(
    val message: String,
    val user: UserData?
)