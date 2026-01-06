// app/src/main/java/com/example/frontend_alp_vp/ui/view/pensi/history.kt
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@Composable
fun HistoryPage(
    navController: NavController,
    viewModel: PensiViewModel = viewModel()
) {
    val historyList by viewModel.historyList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHistory()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Riwayat Pemesanan", fontSize = 22.sp, modifier = Modifier.padding(bottom = 16.dp))

        if (historyList.isEmpty()) {
            Text("Belum ada riwayat pemesanan.", color = Color.Gray)
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(historyList) { history ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(history.event.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("${history.quantity} Tiket")
                                Text("Rp ${history.totalPrice.toDouble().toInt()}", color = Color(0xFFC8A27A))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Status: ${history.status}", color = Color.Green)
                        }
                    }
                }
            }
        }
    }
}