package com.example.frontend_alp_vp.network

import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.model.RegisterRequest
import com.example.frontend_alp_vp.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>
}