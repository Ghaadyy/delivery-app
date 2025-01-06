package com.example.deliveryapp.data.model

data class Address(
    val id: Int,
    val userId: Int,
    val latitude: Double,
    val longitude: Double,
    val street: String? = null,
    val apartment: String? = null,
    val building: String? = null
)
