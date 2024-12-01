package com.example.deliveryapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.model.Restaurant
import com.example.deliveryapp.ui.RestaurantItem
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.serialization.Serializable

@Serializable
object HomePage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(), onNavigateToRestaurant: (Restaurant) -> Unit
) {
    val restaurants by homeViewModel.restaurants.observeAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchRestaurants()
    }

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
            AppNavigationBar()
        },
    ) { padding ->
        if (restaurants == null)
            Text("Loading...")
        else LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(restaurants as List<Restaurant>, key = { rest -> rest.name }) { rest ->
                RestaurantItem(rest, onClick = { onNavigateToRestaurant(rest) })
            }
        }
    }
}
