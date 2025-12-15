
package com.example.frontend_alp_vp.ui.view.pensi

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PensiListScreen(
    items: List<String>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit = {},
    onBack: () -> Unit = {}   // ⬅ BACK KE HOME
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                IconButton(onClick = { onBack() }) {

                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }

                Text(
                    text = "Pentas Kebudayaan",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(items) { index, item ->
                    pensiCard(
                        title = item,
                        onClick = { onItemClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun PensiListPage(navController: NavController) {
    val sample = listOf(
        "Parade Teater 2025",
        "Mirota Craft Batik",
        "Festival Budaya",
        "Pentas Seni Tradisional",
        "Wayang Kulit",
        "Ludruk Surabaya"
    )

    PensiListScreen(
        items = sample,
        onItemClick = { index ->
            navController.navigate("pensi_detail/$index")
        },
        onBack = {
            navController.popBackStack() // ⬅ balik ke HOME
        }
    )
}



@Preview(showBackground = true)
@Composable
fun PensiListPreview() {
    val sample = listOf(
        "Parade Teater 2025",
        "Mirota Craft Batik",
        "Festival Budaya",
        "Pentas Seni Tradisional",
        "Wayang Kulit",
        "Ludruk Surabaya"
    )

    PensiListScreen(items = sample)
}
