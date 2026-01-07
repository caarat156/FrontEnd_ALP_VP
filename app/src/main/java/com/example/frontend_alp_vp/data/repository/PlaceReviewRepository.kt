package com.example.frontend_alp_vp.data.repository

import com.example.frontend_alp_vp.ui.model.PlaceReviewModel
import com.example.frontend_alp_vp.data.service.ApiService

class PlaceReviewRepository(private val apiService: ApiService) {

    suspend fun getReviewsByPlace(placeId: Int): List<PlaceReviewModel> {
        return apiService.getReviewsByPlace(placeId)
    }

    suspend fun getReviewById(reviewId: Int): PlaceReviewModel {
        return apiService.getReviewById(reviewId)
    }

    suspend fun createReview(review: PlaceReviewModel): PlaceReviewModel {
        return apiService.createReview(review)
    }

    suspend fun updateReview(reviewId: Int, review: PlaceReviewModel): PlaceReviewModel {
        return apiService.updateReview(reviewId, review)
    }

    suspend fun deleteReview(reviewId: Int) {
        apiService.deleteReview(reviewId)
    }
}

