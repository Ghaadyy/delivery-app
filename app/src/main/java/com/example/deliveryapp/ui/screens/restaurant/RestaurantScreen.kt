package com.example.deliveryapp.ui.screens.restaurant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.data.model.Restaurant
import com.example.deliveryapp.ui.components.menu.Menu
import com.example.deliveryapp.ui.components.restaurant.RestaurantHeader
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantPage(val id: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    restaurantViewModel: RestaurantViewModel = viewModel(), restaurantId: String, onBack: () -> Unit
) {
    val restaurant by restaurantViewModel.restaurant.observeAsState()

    LaunchedEffect(Unit) {
        restaurantViewModel.fetchRestaurant(restaurantId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Delivery App") }, navigationIcon = {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    "Back",
                    modifier = Modifier.clickable(onClick = onBack)
                )
            })
        },
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier)
                if (restaurant != null) RestaurantHeader(restaurant as Restaurant)

                Menu()
                Spacer(modifier = Modifier)
            }
        },
        bottomBar = {
            AppNavigationBar()
        },
    )
}