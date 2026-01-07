// app/src/main/java/com/example/frontend_alp_vp/model/PensiModels.kt
package com.example.frontend_alp_vp.model

import com.google.gson.annotations.SerializedName

// Wrapper untuk respon umum (Pensi Controller pakai "status")
data class PensiResponse<T>(
    val status: Boolean,
    val message: String?,
    val data: T
)

// Wrapper untuk respon Payment (Payment Controller pakai "success")
data class PaymentResponseWrapper<T>(
    val success: Boolean,
    val message: String?,
    val data: T
)

data class Pensi(
    @SerializedName("performance_event_id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("event_description") val description: String?,
    @SerializedName("venue_address") val venueAddress: String?,
    @SerializedName("image_url") val imageUrl: String?,
    val place: Place? = null,
    @SerializedName("event_schedule") val schedules: List<PensiSchedule> = emptyList()
)

data class Place(
    @SerializedName("place_id") val placeId: Int,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("city_name") val cityName: String? // Dari include location
)

data class PensiSchedule(
    @SerializedName("event_schedule_id") val scheduleId: Int,
    val date: String,
    @SerializedName("start_time") val startTime: String,
    @SerializedName("end_time") val endTime: String,
    val price: Double
)

data class BookingRequest(
    val performanceEventId: Int,
    val eventScheduleId: Int,
    val quantity: Int,
    val userId: Int // Sementara manual, nanti ambil dari Token di backend
)

data class BookingResult(
    @SerializedName("event_booking_id") val bookingId: Int,
    val status: String,
    @SerializedName("total_price") val totalPrice: String
)

data class BookingHistory(
    @SerializedName("event_booking_id") val bookingId: Int,
    @SerializedName("total_price") val totalPrice: String,
    val quantity: Int,
    val status: String,
    @SerializedName("performance_event") val event: Pensi // Relasi ke event
)

// === TAMBAHAN UNTUK CALENDAR ===
data class CalendarDate(
    val day: Int,
    val date: String
)

