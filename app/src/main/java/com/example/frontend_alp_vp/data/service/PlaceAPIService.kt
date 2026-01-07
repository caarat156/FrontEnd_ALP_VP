package com.example.frontend_alp_vp.service

import com.example.frontend_alp_vp.model.PlaceResponse
import com.example.frontend_alp_vp.model.ReviewResponse
import com.example.frontend_alp_vp.model.SingleReviewResponse
import com.example.frontend_alp_vp.model.Place
import retrofit2.http.*

interface PlacesAPIService {
    // --- PLACES ---
    @GET("api/places")
    suspend fun getAllPlaces(): PlaceResponse

    @GET("api/places")
    suspend fun getPlacesByCategory(@Query("categoryId") categoryId: Int): PlaceResponse

    @GET("api/places/{placeId}")
    suspend fun getPlaceById(@Path("placeId") placeId: Int): SinglePlaceResponse

    // --- REVIEWS ---
    @GET("api/reviews/place/{placeId}")
    suspend fun getReviewsByPlaceId(@Path("placeId") placeId: Int): ReviewResponse

    // Butuh Token (Bearer ...)
    @POST("api/reviews")
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Body reviewData: Map<String, Any>
    ): SingleReviewResponse

    @PUT("api/reviews/{reviewId}")
    suspend fun updateReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int,
        @Body reviewData: Map<String, Any>
    ): SingleReviewResponse

    @DELETE("api/reviews/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int
    ): SingleReviewResponse
}

// Tambahan model untuk single place response
data class SinglePlaceResponse(
    val success: Boolean,
    val message: String,
    val data: Place
)