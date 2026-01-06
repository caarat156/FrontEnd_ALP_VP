// app/src/main/java/com/example/frontend_alp_vp/repository/RetrofitClient.kt
package com.example.frontend_alp_vp.repository

import com.example.frontend_alp_vp.service.PensiApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 10.0.2.2 adalah localhost untuk Emulator Android
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val api: PensiApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PensiApiService::class.java)
    }
}