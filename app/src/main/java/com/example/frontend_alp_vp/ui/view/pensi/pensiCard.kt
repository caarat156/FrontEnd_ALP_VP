package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun pensiCard(
    title: String,
    imageUrl: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8D4C4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // --- LOGIKA FIX GAMBAR ---
            val context = LocalContext.current
            var finalUrl = imageUrl?.replace("localhost", "10.0.2.2")

            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context)
                    .data(finalUrl)
                    .crossfade(true)
                    .build()
            )

//            // HACK: Jika URL dari placehold.co, paksa tambah .png
//            if (finalUrl?.contains("placehold.co") == true && !finalUrl!!.contains(".png")) {
//                finalUrl = finalUrl!!.replace("?", ".png?")
//                if (!finalUrl!!.contains(".png")) {
//                    finalUrl = "$finalUrl.png"
//                }
//            }
            Image(
                painter = painter,
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFD4C4B4)),
                contentScale = ContentScale.Crop
            )
//            Image(
//                painter = rememberAsyncImagePainter(model = finalUrl ?: "https://placehold.co/600x400.png"),
//                contentDescription = "Event Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f)
//                    .background(Color(0xFFD4C4B4)),
//                contentScale = ContentScale.Crop
//            )
            // -------------------------

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = title,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
            }
        }
    }
}

// Fungsi helper jam (biar sekalian rapi)
fun formatTimeFromIso(isoString: String): String {
    return try {
        val parsedTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        parsedTime.format(formatter)
    } catch (e: Exception) {
        if (isoString.length >= 5) isoString.take(5) else isoString
    }
}