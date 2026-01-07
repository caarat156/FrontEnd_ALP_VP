// app/src/main/java/com/example/frontend_alp_vp/MainActivity.kt
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


import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // PENTING: Tambahan import
import androidx.compose.runtime.getValue      // PENTING: Tambahan import
import androidx.compose.runtime.remember      // PENTING: Tambahan import
import androidx.compose.ui.platform.LocalContext // PENTING: Tambahan import
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.frontend_alp_vp.ui.theme.FrontEnd_ALP_VPTheme
import com.example.frontend_alp_vp.ui.view.pensi.*
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.Coil
// import com.example.frontend_alp_vp.UserPreferences // Pastikan file UserPreferences.kt sudah dibuat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FrontEnd_ALP_VPTheme {
                // The Activity delegates navigation to AppNavigation
                AppNavigation()

        val imageLoader = ImageLoader.Builder(this)
            .components { add(SvgDecoder.Factory()) }
            .build()
        Coil.setImageLoader(imageLoader)
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
    val userToken = userTokenState ?: "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxLCJpYXQiOjE3Njc3MDc1ODgsImV4cCI6MTc2ODMxMjM4OH0.46fGfeowmQFKcKBX1RPvoKdo-ySYH5IgqsnGB2Fz4cc"

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