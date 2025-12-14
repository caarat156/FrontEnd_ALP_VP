package com.example.frontend_alp_vp.repository

class pensiRepository {

    private val api = retrofitClient.api

    suspend fun getAllPensi() = api.getAllPensi()

    suspend fun getPensiDetail(id: Int) = api.getPensiDetail(id)

    suspend fun getSchedules(id: Int) = api.getSchedules(id)

    suspend fun getCalendar(month: Int, year: Int) =
        api.getCalendar(month, year)

    suspend fun checkoutPayment(
        userId: Int,
        eventId: Int,
        scheduleId: Int,
        quantity: Int
    ) = api.checkout(
        mapOf(
            "userId" to userId,
            "performanceEventId" to eventId,
            "eventScheduleId" to scheduleId,
            "quantity" to quantity
        )
    )
}
