package com.example.frontend_alp_vp.data.dto

import java.math.BigDecimal
import java.time.LocalDateTime

// Place DTO
data class PlaceDTO(
    val placeId: Int,
    val placeCategoryId: Int?,
    val locationId: Int?,
    val placeName: String?,
    val placeDescription: String?,
    val address: String?,
    val gmapsLink: String?,
    val imageUrl: String?,
    val ratingAvg: BigDecimal?
)

// Place Review DTO
data class PlaceReviewDTO(
    val reviewId: Int,
    val userId: Int?,
    val placeId: Int?,
    val rating: Int?,
    val comment: String?,
    val createdAt: LocalDateTime?
)

// Place Category DTO
data class PlaceCategoryDTO(
    val placeCategoryId: Int,
    val categoryName: String?
)

// Location DTO
data class LocationDTO(
    val locationId: Int,
    val cityName: String
)

// User DTO
data class UserDTO(
    val userId: Int,
    val selectedLocation: Int?,
    val name: String?,
    val username: String?,
    val email: String?,
    val phone_number: String?,
    val profilePhoto: String?
)

// Event Booking DTO
data class EventBookingDTO(
    val eventBookingId: Int,
    val userId: Int?,
    val performanceEventId: Int?,
    val totalPrice: BigDecimal?,
    val quantity: Int?,
    val status: String?
)

// Event Schedule DTO
data class EventScheduleDTO(
    val eventScheduleId: Int,
    val performanceEventId: Int?,
    val date: String?,
    val startTime: String?,
    val endTime: String?,
    val price: BigDecimal?
)

// Performance Event DTO
data class PerformanceEventDTO(
    val performanceEventId: Int,
    val placeId: Int?,
    val title: String?,
    val eventDescription: String?,
    val venueAddress: String?,
    val gmapsLink: String?,
    val imageUrl: String?
)

// Reels Content DTO
data class ReelsContentDTO(
    val contentId: Int,
    val userId: Int?,
    val placeId: Int?,
    val contentUrl: String?,
    val caption: String?,
    val createdAt: LocalDateTime?
)

