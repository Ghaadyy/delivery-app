package com.example.deliveryapp.data.model.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val reviewerName: String,
    val restaurantId: Int,
    val rating: Float,
    val comments: String
)
