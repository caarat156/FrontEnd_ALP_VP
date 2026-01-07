package com.example.frontend_alp_vp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.data.service.RetrofitClient
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

    fun submitReview(token: String, placeId: Int, rating: Int, comment: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val body = mapOf(
                    "placeId" to placeId,
                    "rating" to rating,
                    "comment" to comment
                )
                val response = RetrofitClient.apiService.addReview("Bearer $token", body)
                if (response.isSuccessful) {
                    onSuccess()
                }
            } catch (e: Exception) {
                // Handle error (e.g. user already reviewed)
            }
        }
    }
}