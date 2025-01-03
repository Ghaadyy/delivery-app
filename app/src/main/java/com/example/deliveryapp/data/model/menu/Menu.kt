package com.example.deliveryapp.data.model.menu

data class Menu(
    val id: Int,
    val restaurantId: String,
    val sections: List<MenuSection>
)