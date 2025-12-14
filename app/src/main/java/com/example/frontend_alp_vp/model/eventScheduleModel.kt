package com.example.frontend_alp_vp.model

data class eventScheduleModel(
    val eventScheduleId: Int,
    val date: String,
    val startTime: String,
    val endTime: String,
    val price: Double
)
