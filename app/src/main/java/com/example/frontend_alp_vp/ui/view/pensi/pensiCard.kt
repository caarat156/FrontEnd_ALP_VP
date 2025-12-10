package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun pensiCard () {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC1856D))
    ){
        Column(){
            Image()
            Text()
        }

    }
}

@Preview
@Composable
