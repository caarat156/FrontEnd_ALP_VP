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
import com.example.frontend_alp_vp.UserPreferences
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.home.HomeView
import com.example.frontend_alp_vp.ui.view.kuliner.KulinerDetail
import com.example.frontend_alp_vp.ui.view.kuliner.KulinerList
import com.example.frontend_alp_vp.ui.view.souvenir.SouvenirDetail
import com.example.frontend_alp_vp.ui.view.wisata.WisataDetail
import com.example.frontend_alp_vp.ui.view.wisata.WisataList
import com.example.frontend_alp_vp.ui.viewmodel.PlaceViewModel
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.Coil
import com.example.frontend_alp_vp.ui.view.review.AddReview
import com.example.frontend_alp_vp.ui.view.souvenir.SouvenirList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi ImageLoader untuk mendukung format SVG (penting untuk ikon)
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

    // Inisialisasi UserPreferences untuk mengelola sesi/token
    val userPreferences = remember { UserPreferences(context) }
    val userTokenState by userPreferences.authToken.collectAsState(initial = null)

    // Token fallback untuk keperluan development/testing
    val userToken = userTokenState ?: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."

    // Di dalam NavHost pada MainActivity.kt
    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeView(navController) // Tambahkan navController agar Home bisa pindah halaman
        }

        composable("wisata") {
            WisataList(navController, viewModel())
        }

        composable("kuliner") {
            KulinerList(navController, viewModel())
        }
        composable("souvenir") {
            SouvenirList(navController, viewModel())
        }

        // Gunakan rute tunggal yang cerdas untuk semua detail
        composable(
            route = "detail/{placeId}",
            arguments = listOf(navArgument("placeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getInt("placeId") ?: 0
            // Karena kita tidak tahu kategorinya dari URL ini,
            // Anda bisa mengarahkan ke satu view detail umum atau
            // sesuaikan dengan logika di tiap ListCard (lihat poin 2)
            WisataDetail(placeId = placeId, navController = navController)
        }
    }
}