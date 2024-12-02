package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.deliveryapp.data.model.menu.MealOption

@Composable
fun Option(option: MealOption) {
    var checked by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = { isChecked -> checked = isChecked })
        Text("${option.name} ${"+ LBP ${option.price * 89500} ($ ${option.price})"}")
    }
}