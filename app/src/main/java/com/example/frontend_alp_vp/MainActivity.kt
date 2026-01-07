package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.home.HomeView
import com.example.frontend_alp_vp.ui.view.kuliner.KulinerDetail
import com.example.frontend_alp_vp.ui.view.kuliner.KulinerList
import com.example.frontend_alp_vp.ui.view.souvenir.SouvenirDetail
import com.example.frontend_alp_vp.ui.view.souvenir.SouvenirList
import com.example.frontend_alp_vp.ui.view.wisata.WisataDetail
import com.example.frontend_alp_vp.ui.view.wisata.WisataList
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.Coil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi ImageLoader untuk mendukung format SVG
        val imageLoader = ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .build()
        Coil.setImageLoader(imageLoader)

        enableEdgeToEdge()
        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Inisialisasi UserPreferences
    val userPreferences = remember { UserPreferences(context) }
    // Menggunakan fungsi getToken() yang tersedia di UserPreferences.kt
    val userTokenState by userPreferences.getToken().collectAsState(initial = null)

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeView(navController)
        }

        composable("wisata") {
            WisataList(navController = navController, viewModel = viewModel())
        }

        composable("kuliner") {
            KulinerList(navController = navController, viewModel = viewModel())
        }

        composable("souvenir") {
            SouvenirList(navController = navController, viewModel = viewModel())
        }

        // Jalur Detail Wisata
        composable(
            route = "wisataDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            WisataDetail(placeId = placeId, navController = navController)
        }

        // Jalur Detail Kuliner
        composable(
            route = "kulinerDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            KulinerDetail(placeId = placeId, navController = navController)
        }

        // Jalur Detail Souvenir
        composable(
            route = "souvenirDetail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            SouvenirDetail(placeId = placeId, navController = navController)
        }
    }
}