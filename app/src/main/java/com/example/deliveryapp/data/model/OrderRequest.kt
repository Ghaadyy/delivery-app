package com.example.deliveryapp.data.model

data class OrderRequest(
    val restaurantId: Int,
    val mealRequests: List<MealRequest>,
    val addressId: Int,
    val paymentMethod: String,
)