package com.example.deliveryapp.ui.screens.favorite

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.navigation.Screen
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    val screens = listOf(Screen.Home, Screen.Favorite)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Favorites")
                },
            )
        },
        bottomBar = {
            AppNavigationBar(LocalNavController.current, screens)
        },
    ) { padding ->

    }
}