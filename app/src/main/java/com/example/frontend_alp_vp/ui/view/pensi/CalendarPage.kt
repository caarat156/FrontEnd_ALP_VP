package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend_alp_vp.viewModel.PensiViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun CalendarPage(viewModel: PensiViewModel = viewModel()) {
    val pensiList by viewModel.pensiList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPensiList()
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Kalender Acara", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Event Mendatang", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

        LazyColumn {
            items(pensiList) { pensi ->
                pensi.schedules.forEach { schedule ->

                    // --- LOGIKA PARSING TANGGAL DIMULAI ---
                    val parsedDate = remember(schedule.date) {
                        try {
                            // Ambil 10 karakter pertama (yyyy-MM-dd) untuk diparse
                            LocalDate.parse(schedule.date.take(10))
                        } catch (e: Exception) {
                            null
                        }
                    }

                    // Formatter untuk Bulan dan Tahun (Contoh: "Sep 2025")
                    // Menggunakan Locale Indonesia agar nama bulan sesuai (Agustus, September, dll)
                    val monthYearFormatter = DateTimeFormatter.ofPattern("MMM yyyy", Locale("id", "ID"))
                    // --- LOGIKA PARSING TANGGAL SELESAI ---

                    Row(modifier = Modifier.padding(bottom = 24.dp)) {
                        // Kolom Tanggal (Kiri)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(60.dp)
                        ) {
                            // Tampilkan TANGGAL (Angka hari)
                            Text(
                                text = parsedDate?.dayOfMonth?.toString() ?: "--",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            // Tampilkan BULAN & TAHUN
                            Text(
                                text = parsedDate?.format(monthYearFormatter) ?: schedule.date,
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Garis Timeline
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(80.dp) // Anda bisa ganti ke .fillMaxHeight() di dalam IntrinsicSize jika ingin dinamis
                                .background(Color.LightGray)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Card Event
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(pensi.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                // Pastikan fungsi formatTimeFromIso() Anda juga sudah benar menangani ISO String
                                Text("${formatTimeFromIso(schedule.startTime)} - ${formatTimeFromIso(schedule.endTime)}", fontSize = 12.sp, color = Color(0xFFC49A7A))
                                Text(pensi.venueAddress ?: "", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
@Preview
fun CalendarPagePreview() {
    CalendarPage()
}