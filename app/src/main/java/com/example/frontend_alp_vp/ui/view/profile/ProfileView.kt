package com.example.frontend_alp_vp.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.frontend_alp_vp.model.Reel
import com.example.frontend_alp_vp.model.UserData

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel, // Using the Factory-injected ViewModel
    onEditProfileClick: () -> Unit = {}
) {
    val reels by viewModel.userReels.collectAsState()
    val user by viewModel.userData.collectAsState()

    // We trigger a refresh every time this screen appears to ensure data is fresh
    LaunchedEffect(Unit) {
        viewModel.loadProfileData()
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        ProfileHeader(
            user = user, // Pass the whole user object
            onEditProfileClick = onEditProfileClick
        )

        Spacer(modifier = Modifier.height(16.dp))
        Divider(thickness = 1.dp, color = Color.LightGray)

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(1.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(reels) { reel ->
                ReelGridItem(reel)
            }
        }
    }
}

@Composable
fun ProfileHeader(
    user: UserData?,
    onEditProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val baseUrl = "http://10.0.2.2:3000/" // Same base URL as your Edit View

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Box(modifier = Modifier.fillMaxWidth().padding(start = 24.dp)) {
            Text("Profil", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Profile Picture - USING YOUR WORKING LOGIC
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(Color(0xFFD9D9D9))
        ) {
            if (user?.profile_photo != null && user.profile_photo.isNotEmpty()) {
                // Construct the full URL
                val fullUrl = if (user.profile_photo.startsWith("http")) user.profile_photo else baseUrl + user.profile_photo

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(fullUrl)
                        .crossfade(true)
                        // Disable cache to force update (just like your Edit View)
                        .memoryCachePolicy(coil.request.CachePolicy.DISABLED)
                        .diskCachePolicy(coil.request.CachePolicy.DISABLED)
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Name and Username
        Text(
            text = user?.name ?: "Loading...",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "@${user?.username ?: "username"}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Button
        Button(
            onClick = onEditProfileClick,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(42.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFBC8E76))
        ) {
            Text(text = "Edit Profil", fontWeight = FontWeight.Bold, color = Color.Black)
        }
    }
}

@Composable
fun ReelGridItem(reel: Reel) {
    val baseUrl = "http://10.0.2.2:3000/"
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = baseUrl + reel.content_url,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}