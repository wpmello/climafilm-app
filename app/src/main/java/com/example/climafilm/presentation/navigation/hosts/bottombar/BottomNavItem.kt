package com.example.climafilm.presentation.navigation.hosts.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.climafilm.presentation.navigation.routes.bottombar.BottomBarRoutes
import com.example.climafilm.presentation.navigation.routes.home.HomeRoutes


data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

object BottomNavItems {
    val list = listOf(
        BottomNavItem(
            name = "Home",
            route = HomeRoutes.Home.toString(),
            icon = Icons.Default.Home
        ),
        BottomNavItem(
            name = "IA",
            route = BottomBarRoutes.IA.toString(),
            icon = Icons.Default.Face
        ),
        BottomNavItem(
            name = "Clima",
            route = BottomBarRoutes.Clima.toString(),
            icon = Icons.Default.WbSunny
        ),
        BottomNavItem(
            name = "Settings",
            route = BottomBarRoutes.Settings.toString(),
            icon = Icons.Default.Settings
        )
    )
}