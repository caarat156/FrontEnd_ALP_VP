package com.example.frontend_alp_vp.network

import com.example.frontend_alp_vp.model.BookingHistory
import com.example.frontend_alp_vp.model.BookingRequest
import com.example.frontend_alp_vp.model.BookingResult
import com.example.frontend_alp_vp.model.CalendarDate
import com.example.frontend_alp_vp.model.LoginRequest
import com.example.frontend_alp_vp.model.LoginResponse
import com.example.frontend_alp_vp.model.PaymentResponseWrapper
import com.example.frontend_alp_vp.model.Pensi
import com.example.frontend_alp_vp.model.PensiResponse
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

    @DELETE("api/reels/{id}")
    suspend fun deleteReel(
        @Header("Authorization") token: String,
        @Path("id") contentId: Int
    ): Response<Any>

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