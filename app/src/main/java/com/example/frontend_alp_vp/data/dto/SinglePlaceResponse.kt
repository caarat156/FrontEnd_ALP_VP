package com.example.frontend_alp_vp.data.dto

import com.example.frontend_alp_vp.model.Place // Pastikan import ke model Place Anda benar

data class SinglePlaceResponse(
    val success: Boolean,
    val message: String,
    val data: Place
)