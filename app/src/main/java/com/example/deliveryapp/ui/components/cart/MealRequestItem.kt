package com.example.deliveryapp.ui.components.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.data.model.MealRequest

@Composable
fun MealRequestItem(mealRequest: MealRequest, onUpdate: (updatedQuantity: Int) -> Unit, onDelete: () -> Unit) {
    var updatedQuantity by remember { mutableIntStateOf(mealRequest.quantity) }

    val price = mealRequest.price * updatedQuantity

    LaunchedEffect(updatedQuantity) { if(updatedQuantity == 0) onDelete() else onUpdate(updatedQuantity) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(mealRequest.meal.name, fontSize = 18.sp)
                    Text("USD $price", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton({ if(updatedQuantity > 0) updatedQuantity-- }) {
                        Icon(Icons.Filled.Delete, "Delete")
                    }
                    Text(updatedQuantity.toString(), fontWeight = FontWeight.SemiBold)
                    IconButton({ updatedQuantity++ }) {
                        Icon(Icons.Filled.Add, "Add")
                    }
                }
            }
            if(mealRequest.options.isNotEmpty()) Text(mealRequest.options.joinToString { opt -> opt.name })
        }
    }
}