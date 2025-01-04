package com.example.deliveryapp.data.model

data class OrderRequest(
    val restaurantId: Int,
    val mealId: Int,
    val quantity: Int,
    val optionIds: List<Int>,
    val addressId: Int,
    val orderDate: String,
    val paymentMethod: String,
)