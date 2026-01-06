package com.example.frontend_alp_vp.ui.view.pensi

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.viewModel.PensiViewModel
import com.example.frontend_alp_vp.viewModel.UiState

@Composable
fun PaymentPage(
    navController: NavController,
    pensiId: Int,
    scheduleId: Int,
    viewModel: PensiViewModel = viewModel(),
    userToken: String, // Token dari login
    userId: Int // ID user yang login
) {
    val context = LocalContext.current
    val pensiDetail by viewModel.selectedPensi.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    // Load data event saat dibuka
    LaunchedEffect(pensiId) {
        viewModel.loadPensiDetail(pensiId)
    }

    // Handle respon payment
    LaunchedEffect(uiState) {
        if (uiState is UiState.Success && (uiState as UiState.Success).message == "Pembayaran Berhasil!") {
            Toast.makeText(context, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show()
            navController.navigate("history_list") { // Pindah ke history
                popUpTo("pensi_list")
            }
            viewModel.resetUiState()
        }
    }

    pensiDetail?.let { pensi ->
        val schedule = pensi.schedules.find { it.scheduleId == scheduleId }
        val price = schedule?.price ?: 0.0
        val quantity = 1 // Hardcode dulu, bisa dibuat dynamic counter
        val total = price * quantity

        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            // Header
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
                Text("Pembayaran", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Card Info
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(pensi.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Jadwal: ${schedule?.date} | ${schedule?.startTime}", fontSize = 14.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Rincian Biaya
            PaymentRow("Harga Tiket", "Rp ${price.toInt()}")
            PaymentRow("Jumlah", "$quantity Tiket")
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            PaymentRow("Total Bayar", "Rp ${total.toInt()}", isBold = true)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.checkout(userToken, userId, pensiId, scheduleId, quantity)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC49A7A))
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Bayar Sekarang", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun PaymentRow(label: String, value: String, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp)
        Text(value, fontSize = 16.sp, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
    }
}