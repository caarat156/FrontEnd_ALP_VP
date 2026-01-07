package com.example.frontend_alp_vp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.data.service.RetrofitClient
import com.example.frontend_alp_vp.model.PlaceData
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    var places: List<PlaceData> by mutableStateOf(listOf())
        private set

    var isLoading: Boolean by mutableStateOf(false)
        private set

    fun loadPlaces(categoryId: Int? = null, locationId: Int? = null) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.apiService.getAllPlaces(categoryId, locationId)
                if (response.isSuccessful) {
                    places = response.body()?.data ?: listOf()
                }
            } catch (e: Exception) {
                // Log error
            } finally {
                isLoading = false
            }
        }
    }
}