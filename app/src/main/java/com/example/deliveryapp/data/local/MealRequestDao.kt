package com.example.deliveryapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.deliveryapp.data.model.MealRequest

@Dao
interface MealRequestDao {
    @Query("SELECT * FROM meal_requests")
    suspend fun getCart(): List<MealRequest>

    @Insert
    suspend fun addToCart(mealRequest: MealRequest)

    @Update
    suspend fun updateItem(mealRequest: MealRequest)

    @Delete
    suspend fun removeFromCart(mealRequest: MealRequest)

    @Query("DELETE FROM meal_requests")
    suspend fun clearCart()
}