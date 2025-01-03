package com.example.deliveryapp.data.model.menu

data class Meal(
    val id: Int,
    val name: String,
    val ingredients: String,
    val price: Double, // price in USD
    val upgrades: List<MealUpgrade>
)