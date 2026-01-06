// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/PaymentPage.kt
package com.example.frontend_alp_vp.ui.view.pensi

import android.widget.Toast
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
import androidx.compose.ui.tooling.preview.Preview
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
    userToken: String,
    userId: Int
) {
    val context = LocalContext.current
    val pensiDetail by viewModel.selectedPensi.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(pensiId) {
        viewModel.loadPensiDetail(pensiId)
    }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            Toast.makeText(context, "Pembayaran Berhasil!", Toast.LENGTH_SHORT).show()
            // Pindah ke halaman History setelah bayar
            navController.navigate("history_list") {
                popUpTo("pensi_list")
            }
            viewModel.resetUiState()
        } else if (uiState is UiState.Error) {
            Toast.makeText(context, (uiState as UiState.Error).message, Toast.LENGTH_SHORT).show()
            viewModel.resetUiState()
        }
    }

    pensiDetail?.let { pensi ->
        val schedule = pensi.schedules.find { it.scheduleId == scheduleId }
        val price = schedule?.price ?: 0.0

        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, "Back")
                }
                Text("Pembayaran", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(pensi.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Jadwal: ${schedule?.date} | ${schedule?.startTime}", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Text("Total Tagihan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text("Rp ${price.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFC49A7A))
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.checkout(userToken, userId, pensiId, scheduleId, 1)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC49A7A)),
                enabled = uiState !is UiState.Loading
            ) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Bayar Sekarang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
@Preview
fun PaymentPagePreview() {
    // Hanya preview layout tanpa data nyata
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text("Pembayaran", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF4EDE6)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Judul Acara Pensi", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Jadwal: 2024-12-20 | 18:00", color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            Text("Total Tagihan", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                "Rp 100000",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFC49A7A)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC49A7A))
        ) {
            Text("Bayar Sekarang", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}