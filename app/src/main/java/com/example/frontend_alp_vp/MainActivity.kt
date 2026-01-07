// app/src/main/java/com/example/frontend_alp_vp/MainActivity.kt
package com.example.frontend_alp_vp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.navigation.NavController
import coil.ImageLoader
import coil.Coil
import coil.decode.SvgDecoder
import com.example.frontend_alp_vp.datastore.AuthenticationManager
import com.example.frontend_alp_vp.ui.navigation.AppNavigation
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.MyBottomNavigationBar
import com.example.frontend_alp_vp.ui.view.pensi.*
import com.example.frontend_alp_vp.ui.view.profile.EditProfileView
import com.example.frontend_alp_vp.ui.view.profile.ProfileScreen
import com.example.frontend_alp_vp.ui.view.profile.ProfileViewModel
import com.example.frontend_alp_vp.ui.view.reels.ReelsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Setup ImageLoader untuk Coil (Support SVG)
        val imageLoader = ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .build()
        Coil.setImageLoader(imageLoader)

        setContent {
            FrontEnd_ALP_VPTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Memanggil AppNavigation utama yang mengatur alur Login -> MainApp
                    AppNavigation()
                }
            }
        }
    }
}

// Helper Factory untuk membuat ProfileViewModel dengan AuthManager
class ProfileViewModelFactory(private val authManager: AuthenticationManager) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(authManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun MainAppScreen(
    navController: NavController,
    userToken: String,
    onLogout: () -> Unit
) {
    // State untuk Bottom Navigation Bar
    var currentRoute by remember { mutableStateOf("home") }

    // Context & AuthManager
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
                // 1. HOME: Menampilkan List Pensi (Sekarang sudah benar)
                "home" -> {
                    PensiListPage(navController = navController)
                }

                // 2. CALENDAR: Menampilkan Halaman Kalender
                "calendar" -> {
                    CalendarPage()
                }

                // 3. REELS: Menampilkan Reels
                "reels" -> {
                    ReelsScreen()
                }

                // 4. HISTORY: Menampilkan History Page dengan parameter yang dibutuhkan
                "history" -> {
                    HistoryPage(
                        navController = navController,
                        userToken = userToken
                    )
                }

                // 5. PROFILE: Menampilkan Profile dengan ViewModel Factory
                "profile" -> {
                    val profileViewModel: ProfileViewModel = viewModel(
                        factory = ProfileViewModelFactory(authManager)
                    )

                    ProfileScreen(
                        viewModel = profileViewModel,
                        onEditProfileClick = { currentRoute = "edit_profile" }
                    )
                }

                // 6. EDIT PROFILE: Sub-halaman dari Profile
                "edit_profile" -> {
                    EditProfileView(
                        onLogout = onLogout,
                        onNavigateBack = { currentRoute = "profile" }
                    )
                }
            }
        }
    }
}

@Composable
fun PlaceholderScreen(text: String) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "You are on the $text", style = MaterialTheme.typography.headlineMedium)
    }
}