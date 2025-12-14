package com.example.frontend_alp_vp.ui.view.review

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateReview() {
    // State untuk existing review (bisa diganti dari API)
    var rating by remember { mutableStateOf(4) }
    var reviewText by remember { mutableStateOf("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sangat recommended!") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Edit Review",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Place Name
            Text(
                text = "Rujak Cingur & Sop Buntut",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // Rating Section
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Rating",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = if (index < rating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                            contentDescription = "Star ${index + 1}",
                            tint = if (index < rating) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { rating = index + 1 }
                        )
                    }
                }

                Text(
                    text = when (rating) {
                        1 -> "Sangat Buruk"
                        2 -> "Buruk"
                        3 -> "Cukup"
                        4 -> "Bagus"
                        5 -> "Sangat Bagus"
                        else -> ""
                    },
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Review Text Field
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = {
                        if (it.length <= 500) reviewText = it
                    },
                    placeholder = {
                        Text(
                            "Ceritakan pengalaman Anda...",
                            color = Color.Gray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF8B5A3C),
                        unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                        cursorColor = Color(0xFF8B5A3C)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 8
                )

                Text(
                    text = "${reviewText.length}/500",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Update Button
            Button(
                onClick = { /* Handle update */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B5A3C),
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = rating > 0 && reviewText.isNotBlank()
            ) {
                Text(
                    text = "Update Review",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpdateReviewPreview() {
    UpdateReview()
}