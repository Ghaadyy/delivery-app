package com.example.deliveryapp.data.model

enum class OrderStatus(val id: Int, val label: String) {
    CONFIRMING(1, "Assigning a shopper"),
    PREPARING_FETCHING_DRIVER(2, "Assigning a driver"),
    PREPARING_DRIVER_GOING_TO_STORE(3, "Driver is on the way to the store"),
    PREPARING_DRIVER_IN_STORE(4, "Driver waiting for your order"),
    ON_THE_WAY(5, "Driver is on the way to you"),
    DELIVERED(6, "Delivered");

    companion object {
        fun fromId(id: Int): OrderStatus? {
            return entries.find { it.id == id }
        }
    }
}