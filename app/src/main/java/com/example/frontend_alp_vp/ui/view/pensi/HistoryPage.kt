// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/history.kt
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@Composable
fun HistoryPage(
    navController: NavController,
    viewModel: PensiViewModel = viewModel(),
    userToken: String // Token diterima dari MainActivity
) {
    val historyList by viewModel.historyList.collectAsState()

    // Load history saat halaman dibuka
    LaunchedEffect(Unit) {
        viewModel.loadHistory(userToken)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Riwayat Pesanan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (historyList.isEmpty()) {
            Text("Belum ada riwayat transaksi.", color = Color.Gray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(historyList) { booking ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Navigasi ke detail history
                                navController.navigate("history_detail/${booking.bookingId}")
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = booking.event?.title ?: "Event Unknown",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Status: ${booking.status}",
                                    color = if(booking.status == "PAID") Color(0xFF4CAF50) else Color.Red,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Rp ${booking.totalPrice}",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC49A7A)
                                )
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
fun HistoryPagePreview() {
    // Hanya preview layout tanpa data nyata
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Riwayat Pesanan",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text("Belum ada riwayat transaksi.", color = Color.Gray)
    }
}