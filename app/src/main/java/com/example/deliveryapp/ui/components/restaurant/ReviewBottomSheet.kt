package com.example.deliveryapp.ui.components.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.data.model.restaurant.Review
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewBottomSheet(restaurantId: Int, onSubmit: (Review) -> Unit, onDismissRequest: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var rating by remember { mutableFloatStateOf(0f) }
    var comments by remember { mutableStateOf("") }

    fun handleSubmit() {
        onSubmit(Review(reviewerName=name, rating=rating, comments = comments, restaurantId = restaurantId ))
        onDismissRequest()
    }

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column (verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(name, { text -> name = text }, placeholder = { Text("Name...") })
            Text("Rating : $rating ⭐️", fontWeight = FontWeight.SemiBold)
            Slider(value = rating, onValueChange = { rating = (it / 0.5f).roundToInt() * 0.5f }, steps = 10, valueRange = 0f..5f)
            TextField(comments, { text -> comments = text }, placeholder = { Text("Share your thoughts...") })
            Button({ handleSubmit() }) {
                Text("Submit Review")
            }
        }
    }
}