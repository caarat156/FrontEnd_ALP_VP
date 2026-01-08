package com.example.frontend_alp_vp.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import kotlinx.coroutines.launch

// Import screens
import com.example.frontend_alp_vp.MainAppScreen
import com.example.frontend_alp_vp.ui.view.login.LoginView
import com.example.frontend_alp_vp.ui.view.register.RegisterView
import com.example.frontend_alp_vp.ui.view.splash.SplashView
import com.example.frontend_alp_vp.ui.view.pensi.* // Import PensiDetail, Payment, HistoryDetail

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authManager = remember { AuthenticationManager(context) }

    // Ambil token dan userId (jika perlu decode token, lakukan di sini atau di ViewModel)
    // Untuk simplifikasi, kita pass token ke halaman yang butuh
    val userToken by authManager.authToken.collectAsState(initial = null)

    // Fallback token jika null (HANYA UNTUK TESTING, HAPUS SAAT PRODUCTION)
    val finalToken = userToken ?: ""
    val userId = 1 // Ganti dengan logika ambil ID dari token/datastore yang benar

    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Route.Splash.path
    ) {
        // 1. Splash
        composable(Route.Splash.path) {
            SplashView(
                onSplashFinished = {
                    if (userToken != null && userToken!!.isNotEmpty()) {
                        navController.navigate(Route.MainApp.path) {
                            popUpTo(Route.Splash.path) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Route.Login.path) {
                            popUpTo(Route.Splash.path) { inclusive = true }
                        }
                    }
                }
            )
        }

        // 2. Login
        composable(Route.Login.path) {
            LoginView(
                onNavigateToRegister = { navController.navigate(Route.Register.path) },
                onNavigateToHome = {
                    navController.navigate(Route.MainApp.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                }
            )
        }

        // 3. Register
        composable(Route.Register.path) {
            RegisterView(
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        // 4. Main App (Halaman Utama dengan Bottom Bar)
        composable(Route.MainApp.path) {
            MainAppScreen(
                navController = navController, // Pass navController ke MainAppScreen
                userToken = finalToken,
                onLogout = {
                    scope.launch {
                        authManager.clearToken()
                        navController.navigate(Route.Login.path) {
                            popUpTo(Route.MainApp.path) { inclusive = true }
                        }
                    }
                }
            )
        }

        // --- SUB-HALAMAN (Supaya tidak crash saat diklik) ---

        // 5. Pensi Detail
        composable(
            route = "pensi_detail/{pensiId}",
            arguments = listOf(navArgument("pensiId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("pensiId") ?: 0
            PensiDetailPage(navController = navController, pensiId = id)
        }

        // 6. Payment Page
        composable(
            route = "payment/{pensiId}/{scheduleId}",
            arguments = listOf(
                navArgument("pensiId") { type = NavType.IntType },
                navArgument("scheduleId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pId = backStackEntry.arguments?.getInt("pensiId") ?: 0
            val sId = backStackEntry.arguments?.getInt("scheduleId") ?: 0

            PaymentPage(
                navController = navController,
                pensiId = pId,
                scheduleId = sId,
                userToken = finalToken,
                userId = userId
            )
        }

        // 7. History List (Jika diakses via navigasi langsung)
        composable("history_list") {
            HistoryPage(navController = navController, userToken = finalToken)
        }

        // 8. History Detail (E-Ticket)
        composable(
            route = "history_detail/{bookingId}",
            arguments = listOf(navArgument("bookingId") { type = NavType.IntType })
        ) { backStackEntry ->
            val bId = backStackEntry.arguments?.getInt("bookingId") ?: 0
            HistoryDetailPage(
                navController = navController,
                bookingId = bId,
                userToken = finalToken
            )
        }
    }
}