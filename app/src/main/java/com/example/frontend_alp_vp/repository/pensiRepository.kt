// app/src/main/java/com/example/frontend_alp_vp/repository/PensiRepository.kt
package com.example.frontend_alp_vp.repository

import com.example.frontend_alp_vp.model.BookingRequest

class PensiRepository {
    private val api = RetrofitClient.api

    suspend fun getAllPensi() = api.getAllPensi()

    suspend fun getPensiDetail(id: Int) = api.getPensiDetail(id)

    suspend fun checkout(token: String, request: BookingRequest) =
        api.checkout("Bearer $token", request)

    suspend fun getHistory(token: String) = api.getHistory("Bearer $token")

    suspend fun getCalendar(year: Int, month: Int) = api.getCalendar(year, month)
}