package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.restaurant.Review

interface RestaurantsRepository {
    suspend fun fetchRestaurants(): Result<List<Restaurant>>
    suspend fun fetchRestaurant(id: Int): Result<Restaurant>
    suspend fun fetchMenu(restaurantId: Int): Result<Menu>

    suspend fun fetchReviews(restaurantId: Int): Result<List<Review>>
    suspend fun addReview(review: Review): Result<Unit>
}