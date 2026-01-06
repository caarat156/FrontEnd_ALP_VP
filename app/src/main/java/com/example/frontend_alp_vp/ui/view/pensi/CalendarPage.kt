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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@Composable
fun CalendarPage(viewModel: PensiViewModel = viewModel()) {
    val pensiList by viewModel.pensiList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadPensiList() // Load semua event untuk ditampilkan di list
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Kalender Acara", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Event Mendatang", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

        // List Event (Timeline Style)
        LazyColumn {
            items(pensiList) { pensi ->
                pensi.schedules.forEach { schedule ->
                    Row(modifier = Modifier.padding(bottom = 24.dp)) {
                        // Tanggal di kiri
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(60.dp)
                        ) {
                            Text(schedule.date.takeLast(2), fontSize = 20.sp, fontWeight = FontWeight.Bold) // Ambil tanggalnya aja (misal "20")
                            Text(schedule.date.take(7), fontSize = 12.sp, color = Color.Gray) // Bulan/Tahun
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Garis Timeline
                        Box(
                            modifier = Modifier
                                .width(2.dp)
                                .height(80.dp)
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
                                Text("${schedule.startTime} - ${schedule.endTime}", fontSize = 12.sp, color = Color(0xFFC49A7A))
                                Text(pensi.venueAddress ?: "", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }
}