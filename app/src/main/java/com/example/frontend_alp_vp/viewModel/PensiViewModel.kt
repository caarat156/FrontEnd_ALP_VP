// app/src/main/java/com/example/frontend_alp_vp/viewModel/pensiViewModel.kt
package com.example.frontend_alp_vp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.model.*
import com.example.frontend_alp_vp.repository.PensiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PensiViewModel : ViewModel() {
    private val repository = PensiRepository()

    // --- STATE ---
    private val _pensiList = MutableStateFlow<List<Pensi>>(emptyList())
    val pensiList: StateFlow<List<Pensi>> = _pensiList.asStateFlow()

    private val _selectedPensi = MutableStateFlow<Pensi?>(null)
    val selectedPensi: StateFlow<Pensi?> = _selectedPensi.asStateFlow()

    private val _historyList = MutableStateFlow<List<BookingHistory>>(emptyList())
    val historyList: StateFlow<List<BookingHistory>> = _historyList.asStateFlow()

    private val _selectedBooking = MutableStateFlow<BookingHistory?>(null)
    val selectedBooking: StateFlow<BookingHistory?> = _selectedBooking.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    // --- API CALLS ---

    fun loadPensiList() {
        viewModelScope.launch {
            try {
                val response = repository.getAllPensi()
                _pensiList.value = response.data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun loadPensiDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPensiDetail(id)
                _selectedPensi.value = response.data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun loadHistory(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getHistory(token)
                _historyList.value = response.data ?: emptyList()
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun loadBookingDetail(token: String, id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getBookingDetail(token, id)
                _selectedBooking.value = response.data
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    fun checkout(token: String, userId: Int, eventId: Int, scheduleId: Int, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val request = BookingRequest(userId, eventId, scheduleId, quantity)
                val response = repository.checkout(token, request)
                if (response.success) _uiState.value = UiState.Success("Pembayaran Berhasil!")
                else _uiState.value = UiState.Error(response.message ?: "Gagal")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Error")
            }
        }
    }

    fun resetUiState() { _uiState.value = UiState.Idle }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val message: String) : UiState()
    data class Error(val message: String) : UiState()
}