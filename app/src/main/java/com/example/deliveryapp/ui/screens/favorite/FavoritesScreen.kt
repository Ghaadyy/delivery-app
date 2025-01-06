package com.example.deliveryapp.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.restaurant.Favorite
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.ui.components.restaurant.RestaurantItem
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.screens.home.HomeViewModel
import com.example.deliveryapp.ui.screens.restaurant.RestaurantPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(homeViewModel: HomeViewModel = viewModel()) {
    val navController = LocalNavController.current

    val restaurants by homeViewModel.restaurants.observeAsState()
    val favorites by homeViewModel.favorites.observeAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchRestaurants()
        homeViewModel.fetchFavorites()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Favorites", fontWeight = FontWeight.SemiBold)
                },
            )
        },
        bottomBar = { AppNavigationBar() },
    ) { padding ->
        if (restaurants == null) Text("Loading...")
        else LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier)
            }
            items((restaurants as List<Restaurant>).filter { rest ->
                if (favorites == null) false else (favorites as List<Favorite>).contains(
                    Favorite(rest.id)
                )
            }, key = { rest -> rest.id }) { rest ->
                RestaurantItem(rest, favorite = true, onToggleFavorite = { isFavorite ->
                    if (isFavorite) {
                        homeViewModel.likeRestaurant(rest.id)
                    } else {
                        homeViewModel.dislikeRestaurant(rest.id)
                    }
                }, onClick = { navController.navigate(RestaurantPage(rest.id)) })
            }
            item {
                Spacer(modifier = Modifier)
            }
        }
    }
}