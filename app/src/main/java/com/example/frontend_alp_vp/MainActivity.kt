// app/src/main/java/com/example/frontend_alp_vp/MainActivity.kt
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
import com.example.frontend_alp_vp.ui.view.pensi.*

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

    val userToken = userPreferences.getToken().first()
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