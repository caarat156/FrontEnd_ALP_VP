package com.example.frontend_alp_vp.ui.view.wisata

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.frontend_alp_vp.ui.view.review.ReviewCard
import com.example.frontend_alp_vp.ui.viewmodel.PlaceDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WisataDetail(
    placeId: Int,
    navController: NavController,
    viewModel: PlaceDetailViewModel = viewModel()
) {
    val place by viewModel.place.collectAsState()
    val reviews by viewModel.reviews.collectAsState()

    LaunchedEffect(placeId) {
        viewModel.getPlaceDetail(placeId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Wisata") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
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
                Text(text = data.place_name, fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                Text(text = "Destinasi Wisata", color = Color.Gray, modifier = Modifier.padding(bottom = 16.dp))

                AsyncImage(
                    model = data.image_url,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(280.dp).background(Color.LightGray, RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Row(modifier = Modifier.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = data.rating_avg.toString(), fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(4.dp))
                    repeat(data.rating_avg.toInt()) { Icon(Icons.Filled.Star, null, tint = Color(0xFFFFD700)) }
                }

                Text(text = data.place_description)
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Alamat", fontWeight = FontWeight.Bold)
                Text(text = data.address, modifier = Modifier.padding(bottom = 32.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "Review", fontWeight = FontWeight.Bold)
                    Button(onClick = { navController.navigate("add_review/${data.place_id}") }) { Text("+ Add Review") }
                }

                reviews.forEach { review ->
                    ReviewCard(userName = "User ${review.userId}", rating = review.rating, reviewText = review.comment, isOwnReview = false)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        } ?: Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    }
}