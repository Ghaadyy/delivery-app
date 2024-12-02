package com.example.deliveryapp.data.model

import java.io.Serializable

data class Order(
    val id: Int,
    val customerId: Int,
    val restaurantId: String,
    val driver: Driver? = null,
    var orderStatus: OrderStatus,
    val orderDate: String,
    val deliveredDate: String,
    val orderLocation: Pair<Double, Double>,
    val subtotal: Double,
    val discountedPrice: Double,
    val deliveryCharge: Double,
    val paymentMethod: String,
    var orderRating: OrderRating = OrderRating.NOT_APPLICABLE,
    var driverRating: DriverRating = DriverRating.NOT_APPLICABLE,
) : Serializable
