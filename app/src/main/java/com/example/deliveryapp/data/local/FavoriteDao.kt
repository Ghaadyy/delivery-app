package com.example.deliveryapp.data.local

import androidx.room.*
import com.example.deliveryapp.data.model.restaurant.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavoriteRestaurants(): List<Favorite>

    @Insert
    fun likeRestaurant(favorite: Favorite)

    @Delete
    fun dislikeRestaurant(favorite: Favorite)
}