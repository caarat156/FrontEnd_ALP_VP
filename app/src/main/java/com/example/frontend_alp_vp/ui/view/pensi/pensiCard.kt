package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.layout.ContentScale

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

            // Image
            val fixedUrl = imageUrl?.replace("localhost", "10.0.2.2")
                ?: "https://placehold.co/600x400"

            Image(
                painter = rememberAsyncImagePainter(model = fixedUrl),
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFD4C4B4)),
                contentScale = ContentScale.Crop
            )

            // Title
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

fun formatTimeFromIso(isoString: String): String {
    return try {
        // Coba parsing format ISO standar (1970-01-01T10:00:00.000Z)
        val parsedTime = ZonedDateTime.parse(isoString)
        val formatter = DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
        parsedTime.format(formatter)
    } catch (e: Exception) {
        // Jika gagal (mungkin formatnya cuma "10:00:00"), coba ambil 5 karakter pertama
        if (isoString.length >= 5) {
            isoString.take(5)
        } else {
            isoString // Menyerah, tampilkan apa adanya
        }
    }
}


