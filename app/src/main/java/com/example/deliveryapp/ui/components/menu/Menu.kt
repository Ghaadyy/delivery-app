package com.example.deliveryapp.ui.components.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.data.model.menu.Menu

@Composable
fun Menu(menu: Menu) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        menu.sections.forEach { sec ->
            MenuSection(title = sec.name, meals = sec.meals)
        }
    }
}