package com.example.frontend_alp_vp.data

import android.content.Context
import com.example.frontend_alp_vp.service.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Kita buat singleton agar mudah diakses tanpa Factory
object AppConfig {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}