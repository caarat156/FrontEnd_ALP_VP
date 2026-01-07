package com.example.frontend_alp_vp.network

import com.example.frontend_alp_vp.service.PensiApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // 10.0.2.2 is the special IP for Android Emulator to reach localhost
    private const val BASE_URL = "http://10.0.2.2:3000/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

//object RetrofitClient {
//    // 10.0.2.2 adalah localhost untuk Emulator Android
//    private const val BASE_URL = "http://10.0.2.2:3000/"
//
//    val api: PensiApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(PensiApiService::class.java)
//    }
//}