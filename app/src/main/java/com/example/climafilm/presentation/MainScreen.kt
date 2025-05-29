package com.example.climafilm.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.climafilm.presentation.navigation.hosts.bottombar.BottomNavGraph
import com.example.climafilm.presentation.navigation.hosts.bottombar.BottomNavigationBar

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            BottomNavGraph(navHostController = navController)
        }
    }
}