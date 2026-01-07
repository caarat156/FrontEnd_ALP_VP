// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/historyDetail.kt
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.Image
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
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@OptIn(ExperimentalMaterial3Api::class) // Wajib ada untuk TopAppBar
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

    // --- PERBAIKAN UTAMA: PAKAILAH SCAFFOLD ---
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("E-Ticket", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    // Tombol ini sekarang aman dari status bar
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        // Konten utama dimasukkan ke dalam paddingValues agar tidak tertutup Header
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // PENTING: Geser konten ke bawah TopBar
                .padding(16.dp)
        ) {

            // (HAPUS Header Row manual yang lama karena sudah diganti TopAppBar)

            if (booking == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
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
                                text = data.event.title, // Mengakses title event
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // QR Code Dinamis
                            val qrUrl = "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=LokaLens-${data.bookingId}"

                            Image(
                                painter = rememberAsyncImagePainter(model = qrUrl),
                                contentDescription = "QR Code",
                                modifier = Modifier.size(200.dp)
                            )

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
}

// Preview
@Composable
@Preview
fun HistoryDetailPagePreview() {
    // Gunakan Mock NavController untuk preview
    HistoryDetailPage(
        navController = rememberNavController(),
        bookingId = 1,
        userToken = "dummy"
    )
}