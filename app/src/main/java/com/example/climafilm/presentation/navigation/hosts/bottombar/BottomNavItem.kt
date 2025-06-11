package com.example.climafilm.presentation.navigation.hosts.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.climafilm.presentation.navigation.routes.bottombar.BottomBarRoutes
import com.example.climafilm.presentation.navigation.routes.home.HomeRoutes

data class BottomNavItem(
    val name: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

object BottomNavItems {
    val list = listOf(
        BottomNavItem(
            name = "Home",
            route = HomeRoutes.Home.toString(),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            name = "IA",
            route = BottomBarRoutes.IA.toString(),
            selectedIcon = Icons.Filled.Face,
            unselectedIcon = Icons.Outlined.Face
        ),
        BottomNavItem(
            name = "Clima",
            route = BottomBarRoutes.Clima.toString(),
            selectedIcon = Icons.Filled.WbSunny,
            unselectedIcon = Icons.Outlined.WbSunny
        ),
        BottomNavItem(
            name = "Settings",
            route = BottomBarRoutes.Settings.toString(),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        )
    )
}