package com.example.deliveryapp.data.model.menu

data class MenuSection(
    val id: Int,
    val name: String,
    val meals: List<Meal>
)