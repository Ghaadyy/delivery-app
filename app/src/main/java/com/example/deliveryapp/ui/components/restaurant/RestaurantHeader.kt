package com.example.deliveryapp.ui.components.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.ui.components.shared.RatingChip

@Composable
fun RestaurantHeader(restaurant: Restaurant, onRatingClick: () -> Unit) {
    Column (verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Image(
            painter = painterResource(id = R.drawable.dummy_image),
            "description",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                restaurant.name,
                fontWeight = FontWeight.Bold
            )
            RatingChip(restaurant.rating, onClick = onRatingClick)
        }
    }
}