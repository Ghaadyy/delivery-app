package com.example.deliveryapp.data.model

data class Order(
    val id: Int,
    val customerId: Int,
    val restaurantId: String,
    val driverId: Int,
    val status: String,
    val orderDate: String,
    val orderLocation: String,
    val subtotal: Double,
    val discountedPrice: Double,
    val deliveryCharge: Double,
    val paymentMethod: String,
    val orderRating: Int,
    val driverRating: Boolean
)
