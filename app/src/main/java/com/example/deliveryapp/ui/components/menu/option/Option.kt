package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun Option(name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = true, onCheckedChange = {})
        Text("$name ${"+ LBP 360,000 (USD 4.0)"}")
    }
}