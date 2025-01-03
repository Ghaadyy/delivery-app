package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.local.AppDatabase
import com.example.deliveryapp.data.model.restaurant.Favorite
import com.example.deliveryapp.data.model.restaurant.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReviewsRepository(private val context: Context) {
    suspend fun getRestaurantReviews(restaurantId: Int): List<Review> = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val reviewDao = instance.reviewDao()
        val reviews = reviewDao.getRestaurantReviews(restaurantId)
        reviews
    }

    suspend fun addReview(review: Review) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val favoriteDao = instance.reviewDao()
        favoriteDao.addReview(review)
    }
}