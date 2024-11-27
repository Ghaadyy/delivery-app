package com.example.deliveryapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deliveryapp.screens.Home
import com.example.deliveryapp.screens.HomeScreen
import com.example.deliveryapp.screens.Restaurant
import com.example.deliveryapp.screens.RestaurantScreen

@Composable
fun DeliveryApp() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = Home) {
            composable<Home> {
                HomeScreen(onNavigateToRestaurant = {
                    navController.navigate(
                        route = Restaurant
                    )
                })
            }
            composable<Restaurant> { RestaurantScreen(onBack = { navController.popBackStack() }) }
        }
    }
}