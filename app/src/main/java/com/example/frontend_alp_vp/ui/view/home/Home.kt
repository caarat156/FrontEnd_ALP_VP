package com.example.frontend_alp_vp.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.PinDrop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.frontend_alp_vp.ui.navigation.Route
import com.example.frontend_alp_vp.ui.viewmodel.PlaceViewModel

@Composable
fun HomeView(
    navController: NavController,
    placeViewModel: PlaceViewModel = viewModel()
) {
    var searchText by remember { mutableStateOf("") }

    // Mengambil data dari ViewModel
    val places = placeViewModel.places

    // Logika Randomize: Mengacak data setiap kali places berubah
    val randomPlaces = remember(places) {
        places.shuffled().take(10)
    }

    // Trigger ambil data saat layar dibuka
    LaunchedEffect(Unit) {
        placeViewModel.loadPlaces()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Halo, Pengguna",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Bar
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Cari") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            shape = RoundedCornerShape(24.dp),
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) }
        )

        // Kategori Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5E6D3))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Gunakan Route object!
                    CategoryItem("🎵", "Pentas Seni", Modifier.weight(1f)) {
                        navController.navigate(Route.PensiList.route)
                    }
                    CategoryItem("🏖️", "Wisata", Modifier.weight(1f)) {
                        navController.navigate(Route.Wisata.route)
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    CategoryItem("🍴", "Kuliner", Modifier.weight(1f)) {
                        navController.navigate(Route.Kuliner.route)
                    }
                    CategoryItem("🛍️", "Souvenir", Modifier.weight(1f)) {
                        navController.navigate(Route.Souvenir.route)
                    }
                }
            }

        }

        Text(
            "Untuk kamu",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 24.dp, bottom = 12.dp)
        )

        // Horizontal Row - Data Acak
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(randomPlaces) { place ->
                // Pastikan HomeCard Anda menerima parameter 'place'
                HomeCard(place = place, onClick = {
                    navController.navigate("detail/${place.place_id}")
                })
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Grid Bawah - Data Acak Berbeda
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(randomPlaces.reversed()) { place ->
                HomeCardd(place = place, onClick = {
                    navController.navigate("detail/${place.place_id}")
                })
            }
        }
    }
}

@Composable
fun CategoryItem(icon: String, label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        modifier = modifier.clickable { onClick() }.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(60.dp).clip(CircleShape).background(Color(0xFFD4A574)),
            contentAlignment = Alignment.Center
        ) { Text(icon, fontSize = 28.sp) }
        Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
    }
}