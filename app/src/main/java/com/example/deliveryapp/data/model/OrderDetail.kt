package com.example.deliveryapp.data.model

import com.example.deliveryapp.data.model.menu.Meal

data class OrderDetail(
    val id: Int,
    val orderId: Int,
    val meal: Meal,
    val totalPrice: Double,
    val quantity: Int
)
