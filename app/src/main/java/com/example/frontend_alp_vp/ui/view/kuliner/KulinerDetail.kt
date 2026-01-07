package com.example.frontend_alp_vp.ui.view.kuliner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.frontend_alp_vp.ui.view.review.ReviewCard
import com.example.frontend_alp_vp.ui.viewmodel.PlaceDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KulinerDetail(
    placeId: Int,
    viewModel: PlaceDetailViewModel = viewModel(),
    onBackClick: () -> Unit,
    onAddReviewClick: (Int) -> Unit
) {
    // Observasi data dari ViewModel
    val place by viewModel.place.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    // Trigger fetch data saat placeId berubah
    LaunchedEffect(placeId) {
        viewModel.getPlaceDetail(placeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Kuliner") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        place?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
            ) {
                // Title (Dinamis dari database)
                Text(
                    text = data.place_name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                // Subtitle / Category
                Text(
                    text = data.category_name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Image dinamis menggunakan Coil
                if (!data.image_url.isNullOrEmpty()) {
                    AsyncImage(
                        model = data.image_url,
                        contentDescription = data.place_name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(Color.LightGray, RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                            .background(Color(0xFFD4C4B4), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "No Image", color = Color.Gray)
                    }
                }

                // Rating dinamis
                Row(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = data.rating_avg.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    repeat(data.rating_avg.toInt()) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = Color(0xFFFFD700),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Description dinamis
                Text(
                    text = data.place_description,
                    fontSize = 14.sp,
                    color = Color.Black,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                // Alamat dinamis
                Text(text = "Alamat", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(
                    text = data.address,
                    modifier = Modifier.padding(top = 8.dp, bottom = 32.dp)
                )

                // Review Section
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Review (${data.review_count})", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Button(
                        onClick = { onAddReviewClick(data.place_id) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5A3C))
                    ) {
                        Text("+ Add Review")
                    }
                }

                // List Review dari database
                reviews.forEach { review ->
                    ReviewCard(
                        userName = "User ${review.userId}", // Sesuaikan jika ada field username
                        rating = review.rating,
                        reviewText = review.comment,
                        isOwnReview = false // Logika pengecekan user id bisa ditambahkan di sini
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator() // Tampilkan loading saat data belum ada
        }
    }
}