package com.example.deliveryapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deliveryapp.data.model.Order

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrders(orders: List<Order>)

    @Query("SELECT * FROM orders")
    fun getOrders(): List<Order>
}