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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.ui.components.menu.Menu
import com.example.deliveryapp.ui.components.restaurant.RestaurantHeader
import com.example.deliveryapp.ui.components.restaurant.ReviewBottomSheet
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantPage(val id: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(
    restaurantViewModel: RestaurantViewModel = viewModel(), restaurantId: Int, onBack: () -> Unit
) {
    val navController = LocalNavController.current

    val restaurant by restaurantViewModel.restaurant.observeAsState()
    val menu by restaurantViewModel.menu.observeAsState()
    val errorMessage by restaurantViewModel.errorMessage.observeAsState()
    var isSheetVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        restaurantViewModel.fetchRestaurant(restaurantId)
        restaurantViewModel.fetchMenu(restaurantId)
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, withDismissAction = true)
                restaurantViewModel.clearErrors()
            }
        }
    }

    if (isSheetVisible) {
        ReviewBottomSheet(restaurantId, onSubmit = { review ->
            CoroutineScope(Dispatchers.IO).launch {
                restaurantViewModel.addReview(review)
            }
        }) { isSheetVisible = false }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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
                if (restaurant != null) RestaurantHeader(restaurant as Restaurant) {
                    navController.navigate(ReviewsPage(restaurantId))
                }

                Button({ isSheetVisible = true }) {
                    Text("Review")
                }

                if (menu != null) Menu(menu = (menu as Menu))
                Spacer(modifier = Modifier)
            }
        },
        bottomBar = { AppNavigationBar() },
    )
}