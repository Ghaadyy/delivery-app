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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.restaurant.Review
import com.example.deliveryapp.ui.components.restaurant.review.ReviewCard
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.serialization.Serializable

@Serializable
data class ReviewsPage(val restaurantId: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(restaurantId: Int, restaurantViewModel: RestaurantViewModel = viewModel()) {
    val navController = LocalNavController.current

    val restaurant by restaurantViewModel.restaurant.observeAsState()
    val reviews by restaurantViewModel.reviews.observeAsState()


    LaunchedEffect(Unit) {
        restaurantViewModel.fetchRestaurant(restaurantId)
        restaurantViewModel.getRestaurantReviews(restaurantId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (restaurant != null) (restaurant as Restaurant).name else "Loading...") },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Back",
                        modifier = Modifier.clickable(onClick = { navController.popBackStack() })
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

                if (reviews != null)
                        (reviews as List<Review>).forEach { review -> ReviewCard(review) }

                Spacer(modifier = Modifier)
            }
        },
        bottomBar = { AppNavigationBar() },
    )
}