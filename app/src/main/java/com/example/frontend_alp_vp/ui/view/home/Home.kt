package com.example.frontend_alp_vp.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable // PASTIKAN ADA INI
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.navigation.NavController // PASTIKAN ADA INI

@Composable
fun HomeView(navController: NavController) { // Tambahkan parameter navController
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            "Halo, ...",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ... Bagian Search Bar (tetap sama) ...

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF5E6D3))
                .padding(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryItem(
                        icon = "🎵",
                        label = "Pentas Seni",
                        modifier = Modifier.weight(1f),
                        onClick = { /* rute belum ada di MainActivity */ }
                    )
                    CategoryItem(
                        icon = "🏖️",
                        label = "Tempat Wisata",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("wisata") }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CategoryItem(
                        icon = "🍴",
                        label = "Kuliner",
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate("kuliner") }
                    )
                    CategoryItem(
                        icon = "🛍️",
                        label = "Toko Souvenir",
                        modifier = Modifier.weight(1f),
                        onClick = {navController.navigate("souvenir") }
                    )
                }
            }
        }

        // ... Bagian "Untuk Kamu" (tetap sama) ...
    }
}

@Composable
fun CategoryItem(
    icon: String,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .clickable { onClick() } // Menghubungkan klik ke navigasi
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFD4A574)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 28.sp)
        }
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}