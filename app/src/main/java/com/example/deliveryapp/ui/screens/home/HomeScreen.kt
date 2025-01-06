package com.example.deliveryapp.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.restaurant.Favorite
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.ui.components.location.LocationPickerBottomSheet
import com.example.deliveryapp.ui.components.restaurant.RestaurantItem
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.screens.address.CreateAddressPage
import com.example.deliveryapp.ui.screens.cart.CartPage
import com.example.deliveryapp.ui.screens.restaurant.RestaurantPage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    val navController = LocalNavController.current
    val context = LocalContext.current

    val addressViewModel = (context as HomeActivity).addressViewModel
    val addresses by addressViewModel.addresses.observeAsState()

    val restaurants by homeViewModel.restaurants.observeAsState()
    val errorMessage by homeViewModel.errorMessage.observeAsState()
    val favorites by homeViewModel.favorites.observeAsState()
    var isSheetVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homeViewModel.fetchRestaurants()
        homeViewModel.fetchFavorites()
        addressViewModel.getAddresses()
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, withDismissAction = true)
                homeViewModel.clearErrors()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Surface(
                        onClick = { isSheetVisible = true }, modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                "Location",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text("Location", fontWeight = FontWeight.SemiBold)
                        }
                    }
                },
                actions = {
                    IconButton({ navController.navigate(CartPage) }) {
                        Icon(Icons.Filled.ShoppingCart, "My Cart")
                    }
                }
            )
        },
        bottomBar = { AppNavigationBar() },
    ) { padding ->
        if (isSheetVisible) LocationPickerBottomSheet(
            addresses ?: emptyList(),
            onSelect = { addr -> addressViewModel.selectAddress(addr) },
            onClick = {
                navController.navigate(CreateAddressPage)
            }) {
            isSheetVisible = false
        }

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
            items(restaurants as List<Restaurant>, key = { rest -> rest.id }) { rest ->
                RestaurantItem(rest,
                    if (favorites == null) false else (favorites as List<Favorite>).contains(
                        Favorite(rest.id)
                    ),
                    onToggleFavorite = { isFavorite ->
                        if (isFavorite) {
                            homeViewModel.likeRestaurant(rest.id)
                        } else {
                            homeViewModel.dislikeRestaurant(rest.id)
                        }
                    },
                    onClick = { navController.navigate(RestaurantPage(rest.id)) })
            }
            item {
                Spacer(modifier = Modifier)
            }
        }
    }
}
