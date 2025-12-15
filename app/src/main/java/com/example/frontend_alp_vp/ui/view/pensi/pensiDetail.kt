
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun PensiDetailScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }

            Text(
                text = "Parade Teater 2025",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    Color(0xFFD4C4B4),
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text("📷", fontSize = 48.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Alamat", fontWeight = FontWeight.Bold)
        Text(text = "Jl. Genteng Durasim No.29, Surabaya", fontSize = 13.sp)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Harga Tiket", fontWeight = FontWeight.Bold)
        Text(text = "Rp. 75.000,-", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TicketTimeItem("09.00 - 11.00 WIB")
        TicketTimeItem("12.00 - 14.00 WIB")
        TicketTimeItem("16.00 - 18.00 WIB")
        TicketTimeItem("20.00 - 22.00 WIB")
    }
}


@Composable
fun TicketTimeItem(time: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(
                Color(0xFFF4EDE6),
                RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = time, fontSize = 13.sp)

        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFC8A27A)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Beli Tiket", fontSize = 12.sp)
        }
    }
}

@Composable
fun PensiDetail(navController: NavController) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PensiDetailScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun PensiDetail() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            PensiDetailScreen()
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PensiDetailPreview() {
    PensiDetail()
}
