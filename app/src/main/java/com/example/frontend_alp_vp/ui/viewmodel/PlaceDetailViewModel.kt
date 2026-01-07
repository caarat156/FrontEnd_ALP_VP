package com.example.frontend_alp_vp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.model.Place
import com.example.frontend_alp_vp.model.Review
import com.example.frontend_alp_vp.service.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceDetailViewModel : ViewModel() {
    private val _place = MutableStateFlow<Place?>(null)
    val place: StateFlow<Place?> = _place.asStateFlow()

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews.asStateFlow()

    fun getPlaceDetail(placeId: Int) {
        viewModelScope.launch {
            try {
                // 1. Get Place Data
                val placeRes = RetrofitInstance.api.getPlaceById(placeId)
                if (placeRes.success) {
                    _place.value = placeRes.data
                }

                // 2. Get Reviews for this place
                val reviewRes = RetrofitInstance.api.getReviewsByPlaceId(placeId)
                if (reviewRes.success) {
                    _reviews.value = reviewRes.data
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}