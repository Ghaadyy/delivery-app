package com.example.deliveryapp.ui.components.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun Menu() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        MenuSection("Appetizers")
        MenuSection("Burgers")
    }
}