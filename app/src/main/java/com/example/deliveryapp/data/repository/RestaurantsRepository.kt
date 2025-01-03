package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.restaurant.Review

interface RestaurantsRepository {
    suspend fun fetchRestaurants(): List<Restaurant>
    suspend fun fetchRestaurant(id: Int): Restaurant
    suspend fun fetchMenu(restaurantId: Int): Menu

    suspend fun fetchReviews(restaurantId: Int): List<Review>
    suspend fun addReview(review: Review)
}