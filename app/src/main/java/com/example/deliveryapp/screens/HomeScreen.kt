package com.example.deliveryapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.TwoTone.Home,
                        "Home",
                        modifier = Modifier
                            .size(36.dp)
                            .clickable {}
                    )
                    Icon(Icons.TwoTone.Search, "Browse", modifier = Modifier.size(36.dp))
                    Icon(Icons.TwoTone.Settings, "Settings", modifier = Modifier.size(36.dp))
                }
            }
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier)
            }
            items(restaurants) { rest ->
                RestaurantItem(rest, onClick = { onNavigateToRestaurant(rest) })
            }
            item {
                Spacer(modifier = Modifier)
            }
        }
        Spacer(modifier = Modifier)
    }
}
