package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme

// [IMPORTANT] These imports now match your folder structure exactly
import com.example.frontend_alp_vp.ui.view.MyBottomNavigationBar
import com.example.frontend_alp_vp.ui.view.login.LoginView
import com.example.frontend_alp_vp.ui.view.register.RegisterView
import com.example.frontend_alp_vp.ui.view.splash.SplashView
import com.example.frontend_alp_vp.ui.view.profile.EditProfileView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEnd_ALP_VPTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "splash") {

                    // 1. Splash Screen
                    composable("splash") {
                        SplashView(
                            onSplashFinished = {
                                navController.navigate("login") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 2. Login Screen
                    composable("login") {
                        LoginView(
                            onNavigateToRegister = { navController.navigate("register") },
                            onNavigateToHome = {
                                navController.navigate("main_app") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // 3. Register Screen
                    composable("register") {
                        RegisterView(
                            onNavigateToLogin = { navController.popBackStack() }
                        )
                    }

                    // 4. Main App (Home, Calendar, Profile, etc.)
                    composable("main_app") {
                        MainAppScreen(
                            onLogout = {
                                navController.navigate("login") {
                                    popUpTo("main_app") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainAppScreen(onLogout: () -> Unit) {
    var currentRoute by remember { mutableStateOf("home") }

    Scaffold(
        bottomBar = {
            MyBottomNavigationBar(
                currentRoute = currentRoute,
                onNavigate = { newRoute -> currentRoute = newRoute }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentRoute) {
                "home" -> PlaceholderScreen("Home Page")
                "calendar" -> PlaceholderScreen("Calendar Page")
                "reels" -> PlaceholderScreen("Reels Page")
                "history" -> PlaceholderScreen("History Page")
                "edit_profile" -> EditProfileView(
                    onLogout = onLogout,
                    onNavigateBack = { currentRoute = "home" }
                )
            }
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You are on the $text", style = MaterialTheme.typography.headlineMedium)
    }
}