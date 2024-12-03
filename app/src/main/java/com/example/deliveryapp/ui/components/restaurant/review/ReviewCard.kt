package com.example.deliveryapp.ui.components.restaurant.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.data.model.restaurant.Review

@Composable
fun ReviewCard(review: Review) {
    Card (modifier = Modifier.fillMaxWidth()) {
        Column (verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            Text("Rating: ${review.rating} ⭐")
            Text("Name: ${review.reviewerName}")
            Text("Comment: ${review.comments}")
        }
    }
}