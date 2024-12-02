package com.example.deliveryapp.ui.components.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.data.model.menu.Meal

@Composable
fun MenuSection(title: String, meals: List<Meal>) {
    Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
    Column(
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        meals.forEach { meal ->
            MenuItem(meal)
        }
    }
}