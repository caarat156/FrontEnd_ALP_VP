package com.example.frontend_alp_vp.data.repository

import com.example.frontend_alp_vp.ui.model.PlaceCategoryModel
import com.example.frontend_alp_vp.data.service.ApiService

class PlaceCategoryRepository(private val apiService: ApiService) {

    suspend fun getAllCategories(): List<PlaceCategoryModel> {
        return apiService.getAllCategories()
    }

    suspend fun getCategoryById(categoryId: Int): PlaceCategoryModel {
        return apiService.getCategoryById(categoryId)
    }

    suspend fun createCategory(category: PlaceCategoryModel): PlaceCategoryModel {
        return apiService.createCategory(category)
    }

    suspend fun updateCategory(categoryId: Int, category: PlaceCategoryModel): PlaceCategoryModel {
        return apiService.updateCategory(categoryId, category)
    }

    suspend fun deleteCategory(categoryId: Int) {
        apiService.deleteCategory(categoryId)
    }
}

