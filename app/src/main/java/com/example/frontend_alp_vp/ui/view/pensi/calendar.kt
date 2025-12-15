package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Calendar() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Kalender Acara",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "📍 Surabaya",
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Placeholder Calendar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .background(
                    Color(0xFFF4EDE6),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("Calendar UI", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        EventItem(
            title = "Ludruk Suroboyoan: Cak Durasim",
            desc = "Pertunjukan drama tradisional khas Jawa Timur"
        )

        EventItem(
            title = "Festival Rujak Uleg",
            desc = "Festival kuliner tahunan terbesar di Surabaya"
        )
    }
}

@Composable
fun EventItem(title: String, desc: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = desc, fontSize = 13.sp)
    }
}


@Preview(showBackground = true)
@Composable
fun calendarPreview() {
    Calendar(

    )
}

