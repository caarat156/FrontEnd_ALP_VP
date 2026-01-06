// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/payment.kt
package com.example.frontend_alp_vp.ui.view.pensi

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    viewModel: PensiViewModel = viewModel()
) {
    val pensiDetail by viewModel.selectedPensi.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // State quantity
    var quantity by remember { mutableStateOf(1) }

    // Load data event untuk ditampilkan
    LaunchedEffect(pensiId) {
        viewModel.loadPensiDetail(pensiId)
    }

    // Handle status pembayaran
    LaunchedEffect(uiState) {
        when(uiState) {
            is UiState.Success -> {
                Toast.makeText(context, (uiState as UiState.Success).message, Toast.LENGTH_SHORT).show()
                navController.navigate("history") // Pindah ke history setelah sukses
            }
            is UiState.Error -> {
                Toast.makeText(context, (uiState as UiState.Error).message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

    // Cari jadwal yang dipilih dari list
    val selectedSchedule = pensiDetail?.schedules?.find { it.scheduleId == scheduleId }

    Scaffold { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Konfirmasi Pembayaran", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(pensiDetail?.title ?: "Loading...", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Tanggal: ${selectedSchedule?.date ?: "-"}")
                    Text("Jam: ${selectedSchedule?.startTime ?: "-"}")
                    Text("Harga Tiket: Rp ${selectedSchedule?.price?.toInt() ?: 0}")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input Jumlah Tiket
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text("Jumlah Tiket: ")
                IconButton(onClick = { if (quantity > 1) quantity-- }) { Text("-") }
                Text("$quantity", fontSize = 18.sp)
                IconButton(onClick = { quantity++ }) { Text("+") }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Total Harga
            val totalPrice = (selectedSchedule?.price ?: 0.0) * quantity
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Total Pembayaran", style = MaterialTheme.typography.titleMedium)
                Text("Rp ${totalPrice.toInt()}", style = MaterialTheme.typography.titleLarge, color = Color(0xFFC8A27A))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.checkout(pensiId, scheduleId, quantity) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC8A27A))
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Bayar Sekarang")
                }
            }
        }
    }
}