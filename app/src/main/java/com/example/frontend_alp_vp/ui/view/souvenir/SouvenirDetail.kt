package com.example.frontend_alp_vp.ui.view.souvenir

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.frontend_alp_vp.ui.view.review.ReviewCard
import com.example.frontend_alp_vp.ui.viewmodel.PlaceDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SouvenirDetail(
    placeId: Int,
    viewModel: PlaceDetailViewModel = viewModel(),
    onBackClick: () -> Unit,
    onAddReviewClick: (Int) -> Unit
) {
    val place by viewModel.place.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    LaunchedEffect(placeId) {
        viewModel.getPlaceDetail(placeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Oleh-Oleh", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
                Text(
                    text = data.place_name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
                )

                Text(
                    text = "Pusat Oleh-Oleh",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                AsyncImage(
                    model = data.image_url,
                    contentDescription = data.place_name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .background(Color(0xFFD4C4B4), RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = data.rating_avg.toString(), fontWeight = FontWeight.Bold)
                    repeat(data.rating_avg.toInt()) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFD700))
                    }
                }

                Text(
                    text = data.place_description,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Text(text = "Alamat", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = data.address, modifier = Modifier.padding(top = 8.dp, bottom = 32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Review", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Button(
                        onClick = { onAddReviewClick(data.place_id) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5A3C)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("+ Add Review")
                    }
                }

                reviews.forEach { review ->
                    ReviewCard(
                        userName = "User ${review.userId}",
                        rating = review.rating,
                        reviewText = review.comment,
                        isOwnReview = false
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}