package com.example.frontend_alp_vp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.ui.view.MyBottomNavigationBar
import kotlinx.coroutines.launch

// Import semua halaman (View)
import com.example.frontend_alp_vp.ui.view.login.LoginView
import com.example.frontend_alp_vp.ui.view.register.RegisterView
import com.example.frontend_alp_vp.ui.view.splash.SplashView
import com.example.frontend_alp_vp.ui.view.home.HomeView
import com.example.frontend_alp_vp.ui.view.pensi.*
import com.example.frontend_alp_vp.ui.view.reels.ReelsScreen
import com.example.frontend_alp_vp.ui.view.profile.ProfileScreen
import com.example.frontend_alp_vp.ui.view.profile.EditProfileView
import com.example.frontend_alp_vp.ui.view.profile.ProfileViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend_alp_vp.ui.view.wisata.*
import com.example.frontend_alp_vp.ui.view.kuliner.*
import com.example.frontend_alp_vp.ui.view.souvenir.*
import com.example.frontend_alp_vp.ui.view.review.AddReview

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val context = LocalContext.current
    val authManager = remember { AuthenticationManager(context) }
    val scope = rememberCoroutineScope()

    // Ambil Token untuk keperluan ViewModel (Reels/Profile/Payment butuh ini)
    val userTokenState by authManager.authToken.collectAsState(initial = null)
    val token = userTokenState ?: ""

    // Tentukan halaman mana saja yang menampilkan Bottom Bar
    val bottomBarRoutes = listOf(
        Route.Home.route,
        Route.Calendar.route,
        Route.Reels.route,
        Route.History.route,
        Route.Profile.route
    )

    Scaffold(
        bottomBar = {
            // Tampilkan BottomBar hanya jika route saat ini ada di daftar bottomBarRoutes
            if (currentRoute in bottomBarRoutes) {
                MyBottomNavigationBar(
                    currentRoute = currentRoute ?: Route.Home.route,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            // Agar tidak menumpuk stack saat pindah-pindah tab
                            popUpTo(Route.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.Splash.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // 1. SPLASH & AUTH
            composable(Route.Splash.route) {
                SplashView(onSplashFinished = {
                    val target = if (token.isNotEmpty()) Route.Home.route else Route.Login.route
                    navController.navigate(target) {
                        popUpTo(Route.Splash.route) { inclusive = true }
                    }
                })
            }
            composable(Route.Login.route) {
                LoginView(
                    onNavigateToRegister = { navController.navigate(Route.Register.route) },
                    onNavigateToHome = {
                        navController.navigate(Route.Home.route) {
                            popUpTo(Route.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(Route.Register.route) {
                RegisterView(onNavigateToLogin = { navController.popBackStack() })
            }

            // 2. BOTTOM BAR TABS
            composable(Route.Home.route) { HomeView(navController) }
            composable(Route.Calendar.route) { CalendarPage() }
            composable(Route.Reels.route) { ReelsScreen() }
            composable(Route.History.route) { HistoryPage(navController, userToken = token) }
            composable(Route.Profile.route) {
                // Setup ViewModel Factory untuk Profile karena butuh AuthManager
                val factory = ProfileViewModelFactory(authManager)
                ProfileScreen(
                    viewModel = viewModel(factory = factory),
                    onEditProfileClick = { navController.navigate(Route.EditProfile.route) }
                )
            }

            // 3. FITUR PENSI
            composable(Route.PensiList.route) { PensiListPage(navController) }
            composable(
                route = Route.PensiDetail.route,
                arguments = listOf(navArgument("pensiId") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("pensiId") ?: 0
                PensiDetailPage(navController = navController, pensiId = id)
            }
            composable(
                route = Route.Payment.route,
                arguments = listOf(
                    navArgument("pensiId") { type = NavType.IntType },
                    navArgument("scheduleId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val pId = backStackEntry.arguments?.getInt("pensiId") ?: 0
                val sId = backStackEntry.arguments?.getInt("scheduleId") ?: 0
                PaymentPage(navController, pensiId = pId, scheduleId = sId, userToken = token, userId = 1) // UserId sementara 1
            }
            composable(
                route = Route.HistoryDetail.route,
                arguments = listOf(navArgument("bookingId") { type = NavType.IntType })
            ) { backStackEntry ->
                val bId = backStackEntry.arguments?.getInt("bookingId") ?: 0
                HistoryDetailPage(navController, bookingId = bId, userToken = token)
            }

            // 4. FITUR EXPLORE (Wisata, Kuliner, Souvenir)
            composable(Route.Wisata.route) { WisataList(navController = navController) }
            composable(
                route = Route.WisataDetail.route,
                arguments = listOf(navArgument("placeId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("placeId") ?: 0
                WisataDetail(placeId = id, navController = navController)
            }

            composable(Route.Kuliner.route) { KulinerList(navController = navController) }
            composable(
                route = Route.KulinerDetail.route,
                arguments = listOf(navArgument("placeId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("placeId") ?: 0
                KulinerDetail(placeId = id, navController = navController)
            }

            composable(Route.Souvenir.route) { SouvenirList(navController = navController) }
            composable(
                route = Route.SouvenirDetail.route,
                arguments = listOf(navArgument("placeId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("placeId") ?: 0
                SouvenirDetail(placeId = id, navController = navController)
            }

            // 5. FITUR LAIN
            composable(
                route = Route.AddReview.route,
                arguments = listOf(navArgument("placeId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("placeId") ?: 0
                AddReview(placeId = id, navController = navController)
            }
            composable(Route.EditProfile.route) {
                EditProfileView(
                    onNavigateBack = { navController.popBackStack() },
                    onLogout = {
                        scope.launch {
                            authManager.clearToken()
                            navController.navigate(Route.Login.route) {
                                popUpTo(0) { inclusive = true } // Clear stack
                            }
                        }
                    }
                )
            }
        }
    }
}