package com.example.frontend_alp_vp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.ui.model.PlaceModel
import com.example.frontend_alp_vp.ui.model.PlaceCategoryModel
import com.example.frontend_alp_vp.data.repository.PlaceRepository
import com.example.frontend_alp_vp.data.repository.PlaceCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val placeRepository: PlaceRepository,
    private val categoryRepository: PlaceCategoryRepository
) : ViewModel() {

    private val _places = MutableStateFlow<List<PlaceModel>>(emptyList())
    val places: StateFlow<List<PlaceModel>> = _places.asStateFlow()

    private val _categories = MutableStateFlow<List<PlaceCategoryModel>>(emptyList())
    val categories: StateFlow<List<PlaceCategoryModel>> = _categories.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        loadHomeData()
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val placesList = placeRepository.getAllPlaces()
                val categoriesList = categoryRepository.getAllCategories()
                _places.value = placesList
                _categories.value = categoriesList
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load home data"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
