package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Restaurant

interface RestaurantsRepository {
    suspend fun fetchRestaurants(): List<Restaurant>
    suspend fun fetchRestaurant(id: String): Restaurant
    suspend fun fetchMenu(restaurantId: String): Menu
}