package com.example.frontend_alp_vp.ui.view.reels

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frontend_alp_vp.model.Reel

@Composable
fun ReelsScreen(viewModel: ReelsViewModel = viewModel()) {
    val reels by viewModel.reels.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(reels) { reel ->
                    ReelItem(reel)
                }
            }
        }
    }
}

@Composable
fun ReelItem(reel: Reel) {
    val baseUrl = "http://10.0.2.2:3000/"
    val fullUrl = baseUrl + reel.content_url

    // Check if it's a video
    val isVideo = reel.content_url.endsWith(".mp4", ignoreCase = true)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(750.dp) // Full screen height approx
            .background(Color.Black)
    ) {
        // --- HYBRID PLAYER LOGIC ---
        if (isVideo) {
            VideoPlayer(videoUrl = fullUrl, modifier = Modifier.fillMaxSize())
        } else {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fullUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Reel Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // --- OVERLAY INFO ---
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .padding(bottom = 60.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Avatar
                AsyncImage(
                    model = baseUrl + (reel.user?.profile_photo ?: ""),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                // Username
                Text(
                    text = reel.user?.username ?: "Unknown",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            // Caption
            if (!reel.caption.isNullOrEmpty()) {
                Text(text = reel.caption, color = Color.White, fontSize = 14.sp)
            }
        }
    }
}