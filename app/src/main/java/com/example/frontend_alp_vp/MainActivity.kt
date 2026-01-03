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
import com.example.frontend_alp_vp.ui.navigation.AppNavigation // Import the new file
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.MyBottomNavigationBar
import com.example.frontend_alp_vp.ui.view.profile.EditProfileView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEnd_ALP_VPTheme {
                // The Activity now delegates everything to AppNavigation
                AppNavigation()
            }
        }
    }
}

// Keep this helper function here for now, or move it to a new file "MainAppScreen.kt"
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