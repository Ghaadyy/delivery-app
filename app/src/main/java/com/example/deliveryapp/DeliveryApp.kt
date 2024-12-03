package com.example.deliveryapp

import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.deliveryapp.ui.navigation.Screen
import com.example.deliveryapp.ui.screens.favorite.FavoritesScreen
import com.example.deliveryapp.ui.screens.home.HomeScreen
import com.example.deliveryapp.ui.screens.restaurant.RestaurantPage
import com.example.deliveryapp.ui.screens.restaurant.RestaurantScreen

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!")  }

@Composable
fun DeliveryApp() {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        MaterialTheme {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeScreen() }
                composable<RestaurantPage> { backStackEntry ->
                    val restaurant: RestaurantPage = backStackEntry.toRoute()
                    RestaurantScreen(restaurantId = restaurant.id, onBack = { navController.popBackStack() })
                }
                composable(Screen.Favorite.route) {
                    FavoritesScreen()
                }
            }
        }
    }
}