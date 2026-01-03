package com.example.frontend_alp_vp.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import kotlinx.coroutines.launch

// Import your screens
import com.example.frontend_alp_vp.MainAppScreen // From your MainActivity (we will move this later potentially)
import com.example.frontend_alp_vp.ui.view.login.LoginView
import com.example.frontend_alp_vp.ui.view.register.RegisterView
import com.example.frontend_alp_vp.ui.view.splash.SplashView

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val authManager = remember { AuthenticationManager(context) }
    val userToken by authManager.authToken.collectAsState(initial = null)
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = Route.Splash.path // Using Route.kt
    ) {
        // 1. Splash
        composable(Route.Splash.path) {
            SplashView(
                onSplashFinished = {
                    if (userToken != null) {
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

        // 4. Main App
        composable(Route.MainApp.path) {
            MainAppScreen(
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
    }
}