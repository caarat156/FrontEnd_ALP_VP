package com.example.frontend_alp_vp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.service.RetrofitClient
import com.example.frontend_alp_vp.ui.model.PlaceData
import kotlinx.coroutines.launch

class PlaceViewModel : ViewModel() {
    // State yang akan dibaca oleh UI
    var places by mutableStateOf<List<PlaceData>>(listOf())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadPlaces(categoryId: Int? = null) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = RetrofitClient.apiService.getAllPlaces(categoryId, null)
                if (response.isSuccessful) {
                    places = response.body()?.data ?: listOf()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}