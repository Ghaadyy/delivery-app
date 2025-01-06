package com.example.deliveryapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.deliveryapp.ui.navigation.Screen
import com.example.deliveryapp.ui.screens.address.CreateAddressPage
import com.example.deliveryapp.ui.screens.address.CreateAddressScreen
import com.example.deliveryapp.ui.screens.account.AccountScreen
import com.example.deliveryapp.ui.screens.cart.CartPage
import com.example.deliveryapp.ui.screens.cart.CartScreen
import com.example.deliveryapp.ui.screens.favorite.FavoritesScreen
import com.example.deliveryapp.ui.screens.home.HomeScreen
import com.example.deliveryapp.ui.screens.orders.OrdersScreen
import com.example.deliveryapp.ui.screens.restaurant.RestaurantPage
import com.example.deliveryapp.ui.screens.restaurant.RestaurantScreen
import com.example.deliveryapp.ui.screens.restaurant.ReviewsPage
import com.example.deliveryapp.ui.screens.restaurant.ReviewsScreen
import com.example.deliveryapp.ui.theme.AppTheme

val LocalNavController = compositionLocalOf<NavController> { error("No NavController found!") }

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeliveryApp(fragmentManager: FragmentManager) {
    val navController = rememberNavController()

    CompositionLocalProvider(LocalNavController provides navController) {
        AppTheme {
            NavHost(navController, startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { HomeScreen() }
                composable<RestaurantPage> { backStackEntry ->
                    val restaurant: RestaurantPage = backStackEntry.toRoute()
                    RestaurantScreen(
                        restaurantId = restaurant.id,
                        onBack = { navController.popBackStack() })
                }
                composable(Screen.Favorite.route) {
                    FavoritesScreen()
                }
                composable<ReviewsPage> { backStackEntry ->
                    val review: ReviewsPage = backStackEntry.toRoute()
                    ReviewsScreen(review.restaurantId)
                }
                composable(Screen.Orders.route) { OrdersScreen(fragmentManager = fragmentManager) }
                composable(Screen.Account.route) { AccountScreen() }
                composable<CartPage> { CartScreen() }
                composable<CreateAddressPage> { CreateAddressScreen() }
            }
        }
    }
}