package com.example.frontend_alp_vp.ui.view.kuliner

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
import com.example.frontend_alp_vp.ui.view.souvenir.SouvenirListCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KulinerList(
    navController: NavController, viewModel: PlaceViewModel = viewModel()) {
    val places by viewModel.places.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPlaces(categoryId = 1) // ID Kategori Kuliner
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Toko Souvenir",
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
    ) { paddingValues ->
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(places) { item ->
                KulinerListCard(place = item, onClick = {
                    navController.navigate("detail/${item.id}")
                })
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun KulinerListPreview() {
    KulinerList()
}