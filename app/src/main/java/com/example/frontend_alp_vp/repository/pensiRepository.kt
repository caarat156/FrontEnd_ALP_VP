package com.example.frontend_alp_vp.repository

import com.example.frontend_alp_vp.model.BookingRequest
import com.example.frontend_alp_vp.network.RetrofitClient // Pastikan import ini dari network, bukan repository

class PensiRepository {
    // Menggunakan instance dari RetrofitClient di folder network
    private val apiService = RetrofitClient.instance

    suspend fun getAllPensi() = apiService.getAllPensi()

    suspend fun getPensiDetail(id: Int) = apiService.getPensiDetail(id)

    suspend fun checkout(token: String, request: BookingRequest) =
        apiService.checkout("Bearer $token", request)

    suspend fun getHistory(token: String) = apiService.getUserHistory("Bearer $token")


    suspend fun getBookingDetail(token: String, bookingId: Int) =
        apiService.getBookingDetail("Bearer $token", bookingId)
}