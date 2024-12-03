package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.menu.Meal
import com.example.deliveryapp.data.model.menu.MealOption
import com.example.deliveryapp.data.model.menu.MealUpgrade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsBottomSheet(meal: Meal, onDismissRequest: () -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest
    ) {
        Column(Modifier.verticalScroll(state = rememberScrollState())) {
            Image(
                painter = painterResource(id = R.drawable.dummy_image),
                "description",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f),
                contentScale = ContentScale.FillWidth,
            )
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(meal.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Text(
                    meal.ingredients,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("LBP ${meal.price * 89500}", fontWeight = FontWeight.SemiBold)
                    Text("$${meal.price}", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Spacer(Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                meal.upgrades.forEach { upgrade ->
                    OptionSection(upgrade)
                }
            }
        }
    }
}