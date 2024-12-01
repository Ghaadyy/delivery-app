package com.example.deliveryapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.model.Restaurant
import com.example.deliveryapp.ui.RestaurantItem
import kotlinx.serialization.Serializable

@Serializable
object Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigateToRestaurant: (Restaurant) -> Unit) {
    val restaurants: List<Restaurant> = listOf(
        Restaurant("Burger Lovers", "Mansourieh"),
        Restaurant("Cheese on top", "Jal El Dib"),
        Restaurant("McDonalds", "Mansourieh"),
        Restaurant("Burger King", "Mansourieh"),
        Restaurant("Malak El Tawouk", "Mansourieh"),
        Restaurant("Sandwich W Noss", "Mansourieh"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Delivery App")
                },
            )
        },
        bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = {}, enabled = true) {
                    Icon(
                        Icons.Filled.Home,
                        "Home",
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Search,
                        "Browse",
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Filled.Settings,
                        "Settings",
                    )
                }
            })
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(restaurants, key = { rest -> rest.name }) { rest ->
                RestaurantItem(rest, onClick = { onNavigateToRestaurant(rest) })
            }
        }
    }
}
