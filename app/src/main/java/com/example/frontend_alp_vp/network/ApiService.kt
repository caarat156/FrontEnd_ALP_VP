package com.example.frontend_alp_vp.network

import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.model.RegisterRequest
import com.example.frontend_alp_vp.model.RegisterResponse
import com.example.frontend_alp_vp.model.UserResponse
import com.example.frontend_alp_vp.model.ReelResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @GET("api/auth/profile")
    suspend fun getProfile(@Header("Authorization") token: String): UserResponse

    @Multipart
    @PUT("api/auth/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @PartMap data: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part file: MultipartBody.Part?
    ): UserResponse

    @GET("api/reels/me") // Make sure your backend route matches this URL!
    suspend fun getMyReels(
        @Header("Authorization") token: String
    ): Response<ReelResponse>

    // Add this inside your interface
    @GET("api/reels") // Ensure this matches your backend route for "Get All Reels"
    suspend fun getAllReels(): Response<ReelResponse>

    @Multipart
    @POST("api/reels")
    suspend fun uploadReel(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("caption") caption: RequestBody
    ): Response<Any>

}