// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/historyDetail.kt
package com.example.frontend_alp_vp.ui.view.pensi

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@Composable
fun HistoryDetailPage(
    navController: NavController,
    bookingId: Int,
    viewModel: PensiViewModel = viewModel(),
    userToken: String
) {
    val booking by viewModel.selectedBooking.collectAsState()

    // Load detail booking saat halaman dibuka
    LaunchedEffect(bookingId) {
        viewModel.loadBookingDetail(userToken, bookingId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text("E-Ticket", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (booking == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            booking?.let { data ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8F0)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("LokaLens Event", color = Color.Gray, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = data.event?.title ?: "-",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Dummy QR Code
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("QR CODE", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Spacer(modifier = Modifier.height(24.dp))
                        Divider()
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Booking ID", fontSize = 12.sp, color = Color.Gray)
                                Text("#${data.bookingId}", fontWeight = FontWeight.Bold)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Total Paid", fontSize = 12.sp, color = Color.Gray)
                                Text("Rp ${data.totalPrice}", fontWeight = FontWeight.Bold, color = Color(0xFFC49A7A))
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
fun HistoryDetailPagePreview() {
    // Hanya preview layout tanpa data nyata
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }) {
                Icon(Icons.Default.ArrowBack, "Back")
            }
            Text("E-Ticket", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("LokaLens Event", color = Color.Gray, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Contoh Judul Event",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Dummy QR Code
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("QR CODE", color = Color.White, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(24.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Booking ID", fontSize = 12.sp, color = Color.Gray)
                        Text("#123456", fontWeight = FontWeight.Bold)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Paid", fontSize = 12.sp, color = Color.Gray)
                        Text("Rp 150000", fontWeight = FontWeight.Bold, color = Color(0xFFC49A7A))
                    }
                }
            }
        }
    }
}