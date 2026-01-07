package com.example.frontend_alp_vp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.frontend_alp_vp.R
import com.example.frontend_alp_vp.ui.navigation.Route

sealed class BottomNavItem(
    val route: String,
    val icon: Int,
    val activeIcon: Int,
    val label: String
) {
    // Gunakan Route.xxx.route agar konsisten
    object Home : BottomNavItem(Route.Home.route, R.drawable.homenav, R.drawable.homenavactive, "Home")
    object Calendar : BottomNavItem(Route.Calendar.route, R.drawable.calendarnav, R.drawable.calendarnavactive, "Calendar")
    object Reels : BottomNavItem(Route.Reels.route, R.drawable.reelsnav, R.drawable.reelsnavactive, "Reels")
    object History : BottomNavItem(Route.History.route, R.drawable.historynav, R.drawable.historynavactive, "History")
    object Profile : BottomNavItem(Route.Profile.route, R.drawable.profilenav, R.drawable.profilenavactive, "Profile")
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

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = {
                    val iconRes = if (isSelected) item.activeIcon else item.icon
                    Image(
                        painter = painterResource(id = iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Black,
                    unselectedIconColor = Color.Gray
                )
            )
        }
    }


}