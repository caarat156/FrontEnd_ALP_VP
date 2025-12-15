//package com.example.frontend_alp_vp.viewModel
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.frontend_alp_vp.repository.pensiRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class pensiViewModel : ViewModel() {
//
//    private val repository = pensiRepository()
//
//    private val _pensiList = MutableStateFlow<List<Pensi>>(emptyList())
//    val pensiList: StateFlow<List<Pensi>> = _pensiList
//
//    private val _pensiDetail = MutableStateFlow<Pensi?>(null)
//    val pensiDetail: StateFlow<Pensi?> = _pensiDetail
//
//    private val _schedules = MutableStateFlow<List<EventSchedule>>(emptyList())
//    val schedules: StateFlow<List<EventSchedule>> = _schedules
//
//    private val _calendar = MutableStateFlow<List<EventSchedule>>(emptyList())
//    val calendar: StateFlow<List<EventSchedule>> = _calendar
//
//    fun loadPensi() {
//        viewModelScope.launch {
//            _pensiList.value = repository.getAllPensi()
//        }
//    }
//
//    fun loadDetail(id: Int) {
//        viewModelScope.launch {
//            _pensiDetail.value = repository.getPensiDetail(id)
//            _schedules.value = repository.getSchedules(id)
//        }
//    }
//
//    fun loadCalendar(month: Int, year: Int) {
//        viewModelScope.launch {
//            _calendar.value = repository.getCalendar(month, year)
//        }
//    }
//
//    fun checkout(
//        userId: Int,
//        eventId: Int,
//        scheduleId: Int,
//        quantity: Int
//    ) {
//        viewModelScope.launch {
//            repository.checkoutPayment(
//                userId,
//                eventId,
//                scheduleId,
//                quantity
//            )
//        }
//    }
//}
