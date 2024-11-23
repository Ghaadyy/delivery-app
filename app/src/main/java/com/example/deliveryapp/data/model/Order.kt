package com.example.deliveryapp.data.model

import java.io.Serializable

data class Order(
    val id: Int,
    val customerId: Int,
    val restaurantId: String,
    val driverId: String = "-1",
    val status: Int,
    val orderDate: String,
    val deliveredDate: String,
    val orderLocation: String,
    val subtotal: Double,
    val discountedPrice: Double,
    val deliveryCharge: Double,
    val paymentMethod: String,
    val orderRating: Int = -1,
    val driverRating: Int = -1
) : Serializable
