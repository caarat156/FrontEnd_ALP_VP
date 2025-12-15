package com.example.frontend_alp_vp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.frontend_alp_vp.R

// 1. Define the Menu Items
sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val activeIcon: Int,
    val label: String
) {
    object Home : BottomNavItem("home", R.drawable.homenav, R.drawable.homenavactive, "Home")
    object Calendar : BottomNavItem("calendar", R.drawable.calendarnav, R.drawable.calendarnavactive, "Calendar")
    object Reels : BottomNavItem("reels", R.drawable.reelsnav, R.drawable.reelsnavactive, "Reels")
    object History : BottomNavItem("history", R.drawable.historynav, R.drawable.historynavactive, "History")
    object Profile : BottomNavItem("profile", R.drawable.profilenav, R.drawable.profilenavactive, "Profile")

    //bject Profile : BottomNavItem("edit_profile", R.drawable.profilenav, R.drawable.profilenavactive, "Profile")
    //Contoh
}

@Composable
fun MyBottomNavigationBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Calendar,
        BottomNavItem.Reels,
        BottomNavItem.History,
        BottomNavItem.Profile
    )

    // 2. The Navigation Bar Component
    NavigationBar(
        containerColor = Color.White, // White background as requested
        tonalElevation = 8.dp // Adds a subtle shadow so it pops from the content
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    // 3. Logic to switch between Active and Inactive images
                    val iconRes = if (isSelected) item.activeIcon else item.icon

                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp) // Adjust size as needed
                    )
                },
                label = {
                    // Optional: Remove this Text block if you only want Icons
                    Text(item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // Removes the pill shape background on selection
                    selectedIconColor = Color.Black, // Or your app's primary color
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }
}