// app/src/main/java/com/example/frontend_alp_vp/service/PensiApiService.kt
package com.example.frontend_alp_vp.service

import com.example.frontend_alp_vp.model.*
import retrofit2.http.*

interface PensiApiService {

    @GET("api/pensi")
    suspend fun getAllPensi(
        @Query("locationId") locationId: Int? = null
    ): PensiResponse<List<Pensi>>

    @GET("api/pensi/{id}")
    suspend fun getPensiDetail(
        @Path("id") id: Int
    ): PensiResponse<Pensi>

    // Endpoint Payment (Butuh Token)
    @POST("api/payment/checkout")
    suspend fun checkout(
        @Header("Authorization") token: String,
        @Body request: BookingRequest
    ): PaymentResponseWrapper<BookingResult>

    @GET("api/history")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): PensiResponse<List<BookingHistory>>

    @GET("api/calendar")
    suspend fun getCalendar(
        @Query("year") year: Int,
        @Query("month") month: Int
    ): PensiResponse<List<CalendarDate>>


    // HISTORY: Get All User History
    @GET("api/payment/history")
    suspend fun getUserHistory(
        @Header("Authorization") token: String
    ): PaymentResponseWrapper<List<BookingHistory>>

    // HISTORY DETAIL: Get One Booking
    @GET("api/payment/booking/{id}")
    suspend fun getBookingDetail(
        @Header("Authorization") token: String,
        @Path("id") bookingId: Int
    ): PaymentResponseWrapper<BookingHistory>
}