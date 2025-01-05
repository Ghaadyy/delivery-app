package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.restaurant.Review

interface RestaurantsRepository {
    suspend fun fetchRestaurants(token: String): Result<List<Restaurant>>
    suspend fun fetchRestaurant(token: String, id: Int): Result<Restaurant>
    suspend fun fetchMenu(token: String, restaurantId: Int): Result<Menu>

    suspend fun fetchReviews(token: String, restaurantId: Int): Result<List<Review>>
    suspend fun addReview(token: String, review: Review): Result<Unit>
}