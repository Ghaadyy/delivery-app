package com.example.deliveryapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deliveryapp.data.model.restaurant.Review

@Dao
interface ReviewDao {
    @Query("SELECT * FROM reviews WHERE restaurantId = :restaurantId")
    fun getRestaurantReviews(restaurantId: Int): List<Review>

    @Insert
    fun addReview(review: Review)
}