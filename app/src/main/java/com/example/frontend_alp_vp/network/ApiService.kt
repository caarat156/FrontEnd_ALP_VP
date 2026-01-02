package com.example.frontend_alp_vp.network

import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.model.RegisterRequest
import com.example.frontend_alp_vp.model.RegisterResponse
import com.example.frontend_alp_vp.model.UserResponse // <--- THIS WAS MISSING
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    // Note: This is a SUSPEND function (good for Coroutines)
    @GET("me")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): UserResponse

    // Note: This is a CALL function (good for enqueue callbacks)
    @PUT("profile")
    fun updateProfile(
        @Header("Authorization") token: String,
        @Body updateData: Map<String, String>
    ): Call<UserResponse>
}