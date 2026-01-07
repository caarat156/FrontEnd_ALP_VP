package com.example.frontend_alp_vp.api

import com.example.frontend_alp_vp.data.dto.* import com.example.frontend_alp_vp.model.* // Pastikan path model benar
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /* ===================== PLACES (Public) ===================== */

    @GET("places")
    suspend fun getAllPlaces(
        @Query("categoryId") categoryId: Int? = null,
        @Query("locationId") locationId: Int? = null
    ): Response<PlaceResponse>

    @GET("places/{placeId}")
    suspend fun getPlaceById(
        @Path("placeId") placeId: Int
    ): Response<SinglePlaceResponse>

    @GET("places/recommended")
    suspend fun getRecommendedPlaces(
        @Query("locationId") locationId: Int? = null
    ): Response<PlaceResponse>

    @GET("places/popular")
    suspend fun getPopularPlaces(): Response<PlaceResponse>

    /* ===================== REVIEWS (Public & Private) ===================== */

    @GET("reviews/place/{placeId}")
    suspend fun getReviewsByPlaceId(
        @Path("placeId") placeId: Int
    ): Response<ReviewResponse>

    @POST("reviews")
    suspend fun addReview(
        @Header("Authorization") token: String, // Format: "Bearer <token>"
        @Body reviewData: Map<String, @JvmSuppressWildcards Any>
    ): Response<SingleReviewResponse>

    @PUT("reviews/{reviewId}")
    suspend fun updateReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int,
        @Body reviewData: Map<String, @JvmSuppressWildcards Any>
    ): Response<SingleReviewResponse>

    @DELETE("reviews/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: Int
    ): Response<GenericResponse>

    /* ===================== AUTH (Private) ===================== */

    @GET("auth/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<UserResponse>
}