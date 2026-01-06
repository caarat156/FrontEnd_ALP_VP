// app/src/main/java/com/example/frontend_alp_vp/viewModel/PensiViewModel.kt
package com.example.frontend_alp_vp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frontend_alp_vp.model.BookingRequest
import com.example.frontend_alp_vp.model.Pensi
import com.example.frontend_alp_vp.model.PensiSchedule
import com.example.frontend_alp_vp.repository.PensiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class PensiViewModel : ViewModel() {
    private val repository = PensiRepository()

    // State untuk List Pensi
    private val _pensiList = MutableStateFlow<List<Pensi>>(emptyList())
    val pensiList: StateFlow<List<Pensi>> = _pensiList.asStateFlow()

    // State untuk Detail Pensi
    private val _selectedPensi = MutableStateFlow<Pensi?>(null)
    val selectedPensi: StateFlow<Pensi?> = _selectedPensi.asStateFlow()

    // State Status Loading/Error
    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState = _uiState.asStateFlow()

    fun loadPensiList() {
        viewModelScope.launch {
            try {
                val response = repository.getAllPensi()
                _pensiList.value = response.data
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }

    fun loadPensiDetail(id: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getPensiDetail(id)
                _selectedPensi.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun checkout(token: String, eventId: Int, scheduleId: Int, quantity: Int, userId: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val request = BookingRequest(eventId, scheduleId, quantity, userId)
                val response = repository.checkout(token, request)
                if (response.success) {
                    _uiState.value = UiState.Success("Booking Berhasil!")
                } else {
                    _uiState.value = UiState.Error(response.message ?: "Gagal")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }

    private val _historyList = MutableStateFlow<List<BookingHistory>>(emptyList())
    val historyList = _historyList.asStateFlow()

    // State Calendar
    private val _calendarDates = MutableStateFlow<List<CalendarDate>>(emptyList())
    val calendarDates = _calendarDates.asStateFlow()

    // HARDCODED TOKEN (Ganti ini dengan token asli dari Login nanti)
    // Untuk tes, ambil token dari respon Login di Postman
    private val userToken = "TOKEN_DARI_POSTMAN_COPY_PASTE_DISINI"
    private val userId = 1 // Ganti sesuai ID user yang login

    // ... (Fungsi loadPensiList, loadPensiDetail TETAP ADA) ...

    fun loadHistory() {
        viewModelScope.launch {
            try {
                val response = repository.getHistory(userToken)
                _historyList.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadCalendar() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // Calendar bulan mulai dari 0

        viewModelScope.launch {
            try {
                val response = repository.getCalendar(year, month)
                _calendarDates.value = response.data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Fungsi Checkout yang diperbarui
    fun checkout(eventId: Int, scheduleId: Int, quantity: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val request = BookingRequest(eventId, scheduleId, quantity, userId)
                val response = repository.checkout(userToken, request)
                if (response.success) {
                    _uiState.value = UiState.Success("Pembayaran Berhasil!")
                    loadHistory() // Refresh history setelah beli
                } else {
                    _uiState.value = UiState.Error(response.message ?: "Gagal")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "Terjadi kesalahan")
            }
        }
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val message: String) : UiState()
    data class Error(val message: String) : UiState()
}