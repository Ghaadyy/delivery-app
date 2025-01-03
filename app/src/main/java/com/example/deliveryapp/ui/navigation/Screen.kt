package com.example.deliveryapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object Home : Screen("home", "Home", Icons.Filled.Home)
    data object Favorite : Screen("favorite", "Favorite", Icons.Filled.Favorite)
    data object Orders : Screen("orders", "Orders", Icons.Filled.ShoppingCart)
    data object Account : Screen("account", "Account", Icons.Filled.Person)
}