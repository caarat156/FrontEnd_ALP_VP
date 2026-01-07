package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.viewModel.PensiViewModel

@Composable
fun PensiListPage(
    navController: NavController,
    viewModel: PensiViewModel = viewModel()
) {
    val pensiList by viewModel.pensiList.collectAsState()

    // Load data saat pertama kali dibuka
    LaunchedEffect(Unit) {
        viewModel.loadPensiList()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pentas Kebudayaan", fontSize = 22.sp, modifier = Modifier.padding(bottom = 16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(pensiList) { pensi ->
                    // PERBAIKAN DI SINI: Kirim data imageUrl ke Card
                    pensiCard(
                        title = pensi.title,
                        imageUrl = pensi.imageUrl, // <--- JANGAN LUPA BARIS INI
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            // Kirim ID ke halaman detail
                            navController.navigate("pensi_detail/${pensi.id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PensiListPagePreview() {
    PensiListPage(navController = NavController(LocalContext.current))
}