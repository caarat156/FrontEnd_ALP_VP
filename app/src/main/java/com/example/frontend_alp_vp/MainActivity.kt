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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.ui.navigation.AppNavigation
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.MyBottomNavigationBar
import com.example.frontend_alp_vp.ui.view.profile.EditProfileView
import com.example.frontend_alp_vp.ui.view.profile.ProfileScreen
import com.example.frontend_alp_vp.ui.view.profile.ProfileViewModel
import com.example.frontend_alp_vp.ui.view.reels.ReelsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEnd_ALP_VPTheme {
                // The Activity delegates navigation to AppNavigation
                AppNavigation()
            }
        }
    }
}

@Composable
fun MainAppScreen(onLogout: () -> Unit) {
    var currentRoute by remember { mutableStateOf("home") }

    // 1. Get Context and AuthManager to pass to the ViewModel
    val context = LocalContext.current
    val authManager = remember { AuthenticationManager(context) }

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
                "reels" -> ReelsScreen()
                "history" -> PlaceholderScreen("History Page")

                // 2. FIXED: Initialize ProfileViewModel with Factory and pass it
                "profile" -> {
                    val profileViewModel: ProfileViewModel = viewModel(
                        factory = ProfileViewModelFactory(authManager)
                    )

                    ProfileScreen(
                        viewModel = profileViewModel,
                        onEditProfileClick = { currentRoute = "edit_profile" }
                    )
                }

                "edit_profile" -> EditProfileView(
                    onLogout = onLogout,
                    onNavigateBack = { currentRoute = "profile" }
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

// 3. Helper Factory to create ProfileViewModel with AuthManager
class ProfileViewModelFactory(private val authManager: AuthenticationManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(authManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}