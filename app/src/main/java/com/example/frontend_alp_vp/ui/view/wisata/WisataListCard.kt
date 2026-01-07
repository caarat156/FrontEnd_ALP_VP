package com.example.frontend_alp_vp.ui.view.wisata

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.frontend_alp_vp.ui.model.PlaceData

@Composable
fun WisataListCard(
    place: PlaceData, // Data asli dari backend
    navController: NavController
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(160.dp)
            .height(200.dp) // Sedikit ditambah tingginya agar muat teks & rating
            .clickable {
                navController.navigate("detail/${place.place_id}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8D4C4)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image Section - Mengambil URL dari Backend
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFD4C4B4)),
                contentAlignment = Alignment.Center
            ) {
                if (!place.image_url.isNullOrEmpty()) {
                    AsyncImage(
                        model = place.image_url,
                        contentDescription = place.place_name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = "📷",
                        fontSize = 40.sp,
                        color = Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }

            // Information Section - Menampilkan Nama & Rating asli
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8D4C4))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = place.place_name, // Menghapus hardcoded "Rujak Cingur"
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "⭐ ${place.rating_avg} (${place.review_count})", // Rating dinamis
                    fontSize = 11.sp,
                    color = Color.DarkGray
                )
            }
        }
    }
}