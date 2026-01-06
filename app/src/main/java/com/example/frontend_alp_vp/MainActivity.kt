package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.pensi.CalendarPage
import com.example.frontend_alp_vp.ui.view.pensi.HistoryDetailPage
import com.example.frontend_alp_vp.ui.view.pensi.HistoryPage
import com.example.frontend_alp_vp.ui.view.pensi.PaymentPage
import com.example.frontend_alp_vp.ui.view.pensi.PensiDetailPage
import com.example.frontend_alp_vp.ui.view.pensi.PensiListPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Panggil fungsi navigasi utama di sini
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    // 1. Buat Controller Navigasi
    val navController = rememberNavController()

    // 2. Tentukan 'startDestination' (Halaman pertama yang muncul)
    NavHost(navController = navController, startDestination = "pensi_list") {

        // === HALAMAN 1: LIST PENSI ===
        composable("pensi_list") {
            // Memanggil halaman List yang sudah kita buat sebelumnya
            PensiListPage(navController)
        }

        // === HALAMAN 2: DETAIL PENSI ===
        // Menerima parameter {pensiId}
        composable(
            route = "pensi_detail/{pensiId}",
            arguments = listOf(navArgument("pensiId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Ambil ID dari URL navigasi
            val pensiId = backStackEntry.arguments?.getInt("pensiId") ?: 0
            PensiDetailPage(navController, pensiId)
        }

        // === HALAMAN 3: PAYMENT ===
        // Menerima parameter {pensiId} dan {scheduleId}
        composable(
            route = "payment/{pensiId}/{scheduleId}",
            arguments = listOf(
                navArgument("pensiId") { type = NavType.IntType },
                navArgument("scheduleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pId = backStackEntry.arguments?.getInt("pensiId") ?: 0
            val sId = backStackEntry.arguments?.getInt("scheduleId") ?: 0

            // Pastikan kamu sudah punya file PaymentPage.kt
            PaymentPage(navController, pId, sId)
        }

        composable("history_list") {
            HistoryPage(navController, userToken = "TOKEN_USER", userId = 1)
        }

        composable("history_detail/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("bookingId") ?: 0
            HistoryDetailPage(navController, id, userToken = "TOKEN_USER")
        }

        composable("calendar") {
            CalendarPage()
        }
    }
}