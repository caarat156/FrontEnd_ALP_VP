package com.example.frontend_alp_vp.repository

import com.example.frontend_alp_vp.service.RetrofitClient

class PlaceRepository {
    private val api = RetrofitClient.instance

    suspend fun getPlaces(categoryId: Int?) = api.getPlacesByCategory(categoryId)
    suspend fun getReviews(placeId: Int) = api.getReviewsByPlace(placeId)
    suspend fun postReview(request: CreateReviewRequest) = api.createReview(request)
}