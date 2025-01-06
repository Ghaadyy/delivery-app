package com.example.deliveryapp.ui.components.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderStatus

@Composable
fun OrderItem(order: Order, onClick: () -> Unit = {}) {
    val status = OrderStatus.fromId(order.orderStatus)!!
    val message =
        if (status == OrderStatus.DELIVERED) "${status.label} on ${order.deliveredDate}" else status.label

    Card(
        Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(2.75f),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dummy_image),
                        "description",
                        modifier = Modifier.size(56.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        Text(order.restaurantId, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(message, fontSize = 14.sp)
                    }
                }
                IconButton(onClick, modifier = Modifier.weight(0.25f)) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "Go to order")
                }
            }
            Text("Total USD ${order.subtotal}", fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
        }
    }
}