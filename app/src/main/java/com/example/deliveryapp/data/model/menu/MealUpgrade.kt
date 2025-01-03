package com.example.deliveryapp.data.model.menu

data class MealUpgrade(
    val id: Int,
    val name: String,
    val options: List<MealOption>,
    val multiple: Boolean
)
