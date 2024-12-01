package com.example.deliveryapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.deliveryapp.ui.screens.home.Home
import com.example.deliveryapp.ui.screens.home.HomeScreen
import com.example.deliveryapp.ui.screens.restaurant.Restaurant
import com.example.deliveryapp.ui.screens.restaurant.RestaurantScreen

@Composable
fun DeliveryApp() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = Home) {
            composable<Home> {
                HomeScreen(onNavigateToRestaurant = { rest ->
                    navController.navigate(
                        route = Restaurant(rest.name)
                    )
                })
            }
            composable<Restaurant> { backStackEntry ->
                val restaurant: Restaurant = backStackEntry.toRoute()
                RestaurantScreen(title = restaurant.title, onBack = { navController.popBackStack() })
            }
        }
    }
}