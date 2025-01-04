package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.OrderRequest
import com.example.deliveryapp.data.model.menu.Meal
import com.example.deliveryapp.data.model.menu.MealOption
import com.example.deliveryapp.ui.viewModel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionsBottomSheet(meal: Meal, orderViewModel: OrderViewModel = viewModel(), onDismissRequest: () -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState()
    var options by remember { mutableStateOf<List<MealOption>>(listOf()) }
    var quantity by remember { mutableIntStateOf(1) }

    fun addOrder() {
        if(quantity <= 0) return
        val request = OrderRequest(
            1,
            meal.id,
            quantity,
            options.map { it.id },
            1,
            "2025-01-04",
            "Cash"
        )
        orderViewModel.addOrder(request)
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest, sheetState = bottomSheetState
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Column(
                Modifier.verticalScroll(state = rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
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
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(meal.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(
                        meal.ingredients,
                        fontSize = 14.sp,
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("LBP ${meal.price * 89500}", fontWeight = FontWeight.SemiBold)
                        Text("$${meal.price}", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        if(quantity > 0) quantity--
                    }) {
                        Text("-")
                    }

                    Text("$quantity", fontWeight = FontWeight.Bold, fontSize = 20.sp)

                    Button(onClick = {
                        quantity++
                    }) {
                        Text("+")
                    }
                }
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    meal.upgrades.forEach { upgrade ->
                        OptionSection(upgrade) { selectedOption, selectedOptions ->
                            if (selectedOption != null) options = options.plus(selectedOption)
                            if (selectedOptions.isNotEmpty()) options =
                                options.plus(selectedOptions)
                        }
                    }
                }
            }

            ExtendedFloatingActionButton(
                onClick = { addOrder() },
                text = { Text("Add to cart") },
                icon = { Icon(Icons.Filled.ShoppingCart, "Add to cart") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}