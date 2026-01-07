package com.example.frontend_alp_vp

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.home.HomeView
import com.example.frontend_alp_vp.ui.view.kuliner.KulinerList
import com.example.frontend_alp_vp.ui.view.wisata.WisataList
import com.example.frontend_alp_vp.ui.viewmodel.PlaceDetailViewModel
import com.example.frontend_alp_vp.ui.viewmodel.PlaceViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontEnd_ALP_VPTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    // Di dalam MainActivity.kt
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeView(navController) }

        // Route Wisata
        composable("wisata") {
            val viewModel: PlaceViewModel = viewModel()
            WisataList(navController, viewModel)
        }

        // Route Detail (Penting untuk sinkronisasi ID dari Backend)
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            val detailViewModel: PlaceDetailViewModel = viewModel()
            // Panggil view detail kamu di sini
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrontEnd_ALP_VPTheme {
        HomeView()
    }
}