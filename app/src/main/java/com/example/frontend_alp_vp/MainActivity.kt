// app/src/main/java/com/example/frontend_alp_vp/MainActivity.kt
package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // PENTING: Tambahan import
import androidx.compose.runtime.getValue      // PENTING: Tambahan import
import androidx.compose.runtime.remember      // PENTING: Tambahan import
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // PENTING: Tambahan import
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.pensi.*
// import com.example.frontend_alp_vp.UserPreferences // Pastikan file UserPreferences.kt sudah dibuat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    // 1. Ambil Context saat ini (diperlukan untuk DataStore)
    val context = LocalContext.current

    // 2. Inisialisasi UserPreferences (gunakan remember agar tidak dibuat ulang terus menerus)
    // Pastikan Anda sudah membuat file UserPreferences.kt seperti instruksi sebelumnya!
    val userPreferences = remember { UserPreferences(context) }

    // 3. Ambil token secara reaktif (Real-time update)
    // collectAsState mengubah Flow data menjadi State yang dimengerti Compose
    val userTokenState by userPreferences.getToken().collectAsState(initial = null)

    // 4. Logika Token (Fallback untuk Testing)
    // Jika token di DataStore masih kosong (belum login), pakai token hardcode dari Postman.
    // NANTI SAAT FITUR LOGIN JADI: Hapus bagian `?: "TOKEN_..."`
    val userToken = userTokenState ?: "MASUKKAN_TOKEN_DARI_POSTMAN_DISINI"

    val userId = 1 // Ganti sesuai ID user di database

    NavHost(navController = navController, startDestination = "pensi_list") {

        // 1. LIST PENSI
        composable("pensi_list") {
            PensiListPage(navController)
        }

        // 2. DETAIL PENSI
        composable(
            "pensi_detail/{pensiId}",
            arguments = listOf(navArgument("pensiId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("pensiId") ?: 0
            PensiDetailPage(navController, id)
        }

        // 3. PAYMENT
        composable(
            "payment/{pensiId}/{scheduleId}",
            arguments = listOf(
                navArgument("pensiId") { type = NavType.IntType },
                navArgument("scheduleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pId = backStackEntry.arguments?.getInt("pensiId") ?: 0
            val sId = backStackEntry.arguments?.getInt("scheduleId") ?: 0
            PaymentPage(navController, pId, sId, userToken = userToken, userId = userId)
        }

        // 4. HISTORY LIST
        composable("history_list") {
            HistoryPage(navController, userToken = userToken)
        }

        // 5. HISTORY DETAIL (E-TICKET)
        composable(
            "history_detail/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            HistoryDetailPage(navController, bId, userToken = userToken)
        }

        // 6. CALENDAR
        composable("calendar") {
            CalendarPage()
        }
    }
}