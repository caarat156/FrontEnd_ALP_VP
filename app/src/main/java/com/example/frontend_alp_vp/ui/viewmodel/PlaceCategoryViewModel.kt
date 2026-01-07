package com.example.frontend_alp_vp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.ui.model.PlaceCategoryModel
import com.example.frontend_alp_vp.data.repository.PlaceCategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceCategoryViewModel(
    private val categoryRepository: PlaceCategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<PlaceCategoryModel>>(emptyList())
    val categories: StateFlow<List<PlaceCategoryModel>> = _categories.asStateFlow()

    private val _currentCategory = MutableStateFlow<PlaceCategoryModel?>(null)
    val currentCategory: StateFlow<PlaceCategoryModel?> = _currentCategory.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadAllCategories() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val categoriesList = categoryRepository.getAllCategories()
                _categories.value = categoriesList
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load categories"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getCategoryById(categoryId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val category = categoryRepository.getCategoryById(categoryId)
                _currentCategory.value = category
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load category"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createCategory(category: PlaceCategoryModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                categoryRepository.createCategory(category)
                loadAllCategories()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to create category"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateCategory(categoryId: Int, category: PlaceCategoryModel) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                categoryRepository.updateCategory(categoryId, category)
                loadAllCategories()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to update category"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteCategory(categoryId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                categoryRepository.deleteCategory(categoryId)
                loadAllCategories()
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to delete category"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}

