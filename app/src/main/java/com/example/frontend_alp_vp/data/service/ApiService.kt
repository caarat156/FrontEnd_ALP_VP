package com.example.frontend_alp_vp.api

import com.example.frontend_alp_vp.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Places - Public Routes
    @GET("places")
    suspend fun getAllPlaces(
        @Query("categoryId") categoryId: Int?,
        @Query("locationId") locationId: Int?
    ): Response<PlaceResponse>

    @GET("places/recommended")
    suspend fun getRecommendedPlaces(@Query("locationId") locationId: Int?): Response<PlaceResponse>

    // Reviews - Private Routes (Membutuhkan Bearer Token)
    @POST("reviews")
    suspend fun addReview(
        @Header("Authorization") token: String,
        @Body reviewRequest: Map<String, Any>
    ): Response<ReviewResponse>

    @DELETE("reviews/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int
    ): Response<GenericResponse>
}