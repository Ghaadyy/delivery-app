package com.example.deliveryapp.ui.components.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun RatingChip(rating: Double, onClick: () -> Unit = {}) {
    AssistChip(
        onClick = onClick,
        label = { Text("$rating") },
//        enabled = false,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledLabelColor = Color.Black,
            disabledLeadingIconContentColor = Color.Black,

        ),
        leadingIcon = {
            Icon(Icons.Filled.Star, "Rating")
        }
    )
}