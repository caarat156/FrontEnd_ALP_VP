package com.example.frontend_alp_vp.model

// send to /auth/login
data class LoginRequest(
    val email: String,
    val password: String
)

// receive from /auth/login
data class LoginResponse(
    val message: String,
    val token: String?,
    val user: UserData?
)

// send to /auth/register
data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)

// receive from /auth/register
data class RegisterResponse(
    val message: String,
    val user: UserData?
)

// Helper object for User details
data class UserData(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)