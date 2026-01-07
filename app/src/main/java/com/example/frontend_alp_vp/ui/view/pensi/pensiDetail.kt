// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/pensiDetail.kt
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.frontend_alp_vp.model.PensiSchedule
import com.example.frontend_alp_vp.viewModel.PensiViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PensiDetailPage(
    navController: NavController,
    pensiId: Int, // Didapat dari navigasi
    viewModel: PensiViewModel = viewModel()
) {
    val pensiDetail by viewModel.selectedPensi.collectAsState()

    LaunchedEffect(pensiId) {
        viewModel.loadPensiDetail(pensiId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Acara", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Konten halaman dimuat di dalam paddingValues
        pensiDetail?.let { pensi ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // PENTING: Pakai padding dari Scaffold agar tidak tertutup TopBar
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // LOGIKA GAMBAR (Backend -> Emulator)
                val fixedUrl = pensi.imageUrl?.replace("localhost", "10.0.2.2")
                val painter = rememberAsyncImagePainter(
                    model = fixedUrl ?: "https://placehold.co/600x400"
                )

                Image(
                    painter = painter, // PENTING: Gunakan variabel painter yang sudah dibuat di atas
                    contentDescription = "Event Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp) // Berikan tinggi fix agar gambar muncul
                        .background(Color(0xFFD4C4B4)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(pensi.title, fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
                Text(pensi.venueAddress ?: "Lokasi tidak tersedia", color = Color.Gray)

                Spacer(modifier = Modifier.height(8.dp))
                Text(pensi.description ?: "", fontSize = 14.sp)

                Spacer(modifier = Modifier.height(24.dp))
                Text("Jadwal & Tiket", fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)

                // Menampilkan list jadwal dari backend
                pensi.schedules.forEach { schedule ->
                    ScheduleItem(schedule) {
                        // Navigasi ke Payment bawa ID jadwal
                        navController.navigate("payment/${pensi.id}/${schedule.scheduleId}")
                    }
                }
            }
        } ?: Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun ScheduleItem(schedule: PensiSchedule, onBuy: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column {
                Text(schedule.date, style = MaterialTheme.typography.bodyMedium)
                // Panggil fungsi formatTimeFromIso dari pensiCard.kt
                Text(
                    "${formatTimeFromIso(schedule.startTime)} - ${formatTimeFromIso(schedule.endTime)} WIB",
                    style = MaterialTheme.typography.bodySmall
                )
                Text("Rp ${schedule.price.toInt()}", style = MaterialTheme.typography.titleMedium, color = Color(0xFFC8A27A))
            }
            Button(onClick = onBuy, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8A27A))) {
                Text("Beli")
            }
        }
    }
}

@Composable
@Preview
fun PensiDetailPagePreview() {
    // Hanya preview layout tanpa data nyata
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Gambar Placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Gambar Acara", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Judul Acara Pensi", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
        Text("Alamat Lokasi Acara", color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))
        Text("Deskripsi singkat...", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Text("Jadwal & Tiket", fontSize = 18.sp, style = MaterialTheme.typography.titleMedium)

        // Contoh Jadwal
        ScheduleItem(
            schedule = PensiSchedule(
                scheduleId = 1,
                date = "2024-12-20",
                startTime = "10:00",
                endTime = "12:00",
                price = 50000.0
            )
        ) {}
    }
}