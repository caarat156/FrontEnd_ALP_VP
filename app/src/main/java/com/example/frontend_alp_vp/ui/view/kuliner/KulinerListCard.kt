package com.example.frontend_alp_vp.ui.view.kuliner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun KulinerListCard() {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp),
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFFD4C4B4)),
                contentAlignment = Alignment.Center
            ) {
                // Placeholder untuk gambar
                Text(
                    text = "📷",
                    fontSize = 40.sp,
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            }

            // Text
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8D4C4))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Rujak Cingur & Sop",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun KulinerListCardPreview() {
    KulinerListCard()
}