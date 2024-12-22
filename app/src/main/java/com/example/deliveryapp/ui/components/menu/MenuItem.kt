package com.example.deliveryapp.ui.components.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.menu.Meal
import com.example.deliveryapp.ui.components.menu.option.OptionsBottomSheet

@Composable
fun MenuItem(meal: Meal) {
    var isSheetVisible by remember { mutableStateOf(false) }

    if (isSheetVisible) {
        OptionsBottomSheet (meal) { isSheetVisible = false }
    }

    Surface(onClick = { isSheetVisible = true }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(meal.name, fontSize = 18.sp)
                Text(
                    meal.ingredients,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("LBP ${meal.price * 89500}", fontWeight = FontWeight.Bold)
                    Text("$${meal.price}", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.dummy_image),
                "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Red),
            )
        }
    }
}