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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserResponse

    @Multipart
    @PUT("auth/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part?
    ): UserResponse
}