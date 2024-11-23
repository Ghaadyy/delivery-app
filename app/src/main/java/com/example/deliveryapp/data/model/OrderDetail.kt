package com.example.deliveryapp.data.model

data class OrderDetail(
    val id: Int,
    val orderId: Int,
    val itemId: String,
    val totalPrice: Double,
    val quantity: Int
)
