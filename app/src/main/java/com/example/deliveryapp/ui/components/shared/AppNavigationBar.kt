package com.example.deliveryapp.ui.components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.ui.navigation.Screen

@Composable
fun AppNavigationBar() {
    val navController = LocalNavController.current
    val screens = listOf(Screen.Home, Screen.Favorite, Screen.Orders, Screen.Account)

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            screens.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    label = { Text(screen.title) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route)
                            navController.navigate(screen.route)
                    }
                )
            }
        }
    }
}