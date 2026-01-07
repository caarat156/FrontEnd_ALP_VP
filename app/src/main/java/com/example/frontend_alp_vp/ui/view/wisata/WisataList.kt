package com.example.frontend_alp_vp.ui.view.wisata

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WisataList(navController: NavController, viewModel: PlaceViewModel = viewModel()) {
    val places by viewModel.places.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Ambil data saat layar dibuka (Misal: ID Kategori Wisata = 1)
    LaunchedEffect(Unit) {
        viewModel.fetchPlaces(categoryId = 3)
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tempat Wisata",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { padding ->
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(places) { place ->
                    // Kirim object place ke card
                    WisataListCard(place = place, onClick = {
                        navController.navigate("detail/${place.id}")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WisataListPreview() {
    WisataList()
}