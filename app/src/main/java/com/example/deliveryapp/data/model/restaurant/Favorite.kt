package com.example.deliveryapp.data.model.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(@PrimaryKey val restaurantId: Int)
