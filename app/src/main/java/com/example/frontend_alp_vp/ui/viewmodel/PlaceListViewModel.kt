package com.example.frontend_alp_vp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.ui.model.PlaceModel
import com.example.frontend_alp_vp.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceListViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val _places = MutableStateFlow<List<PlaceModel>>(emptyList())
    val places: StateFlow<List<PlaceModel>> = _places.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadAllPlaces() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val placesList = placeRepository.getAllPlaces()
                _places.value = placesList
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load places"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createPlace(place: PlaceModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                placeRepository.createPlace(place)
                loadAllPlaces()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create place"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePlace(placeId: Int, place: PlaceModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                placeRepository.updatePlace(placeId, place)
                loadAllPlaces()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update place"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePlace(placeId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                placeRepository.deletePlace(placeId)
                loadAllPlaces()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete place"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

