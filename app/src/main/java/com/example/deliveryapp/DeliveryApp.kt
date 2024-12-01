package com.example.deliveryapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.deliveryapp.ui.screens.home.HomePage
import com.example.deliveryapp.ui.screens.home.HomeScreen
import com.example.deliveryapp.ui.screens.restaurant.RestaurantPage
import com.example.deliveryapp.ui.screens.restaurant.RestaurantScreen

@Composable
fun DeliveryApp() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = HomePage) {
            composable<HomePage> {
                HomeScreen(onNavigateToRestaurant = { rest ->
                    navController.navigate(
                        route = RestaurantPage(rest.name)
                    )
                })
            }
            composable<RestaurantPage> { backStackEntry ->
                val restaurant: RestaurantPage = backStackEntry.toRoute()
                RestaurantScreen(restaurantId = restaurant.id, onBack = { navController.popBackStack() })
            }
        }
    }
}