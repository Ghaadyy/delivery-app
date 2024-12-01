package com.example.deliveryapp.ui.screens.restaurant

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.R
import com.example.deliveryapp.model.Restaurant
import com.example.deliveryapp.ui.components.menu.Menu
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.serialization.Serializable

@Serializable
data class RestaurantPage(val id: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantScreen(restaurantViewModel: RestaurantViewModel = viewModel(), restaurantId: String, onBack: () -> Unit) {
    val restaurant by restaurantViewModel.restaurant.observeAsState()

    LaunchedEffect(Unit) {
        restaurantViewModel.fetchRestaurant(restaurantId)
    }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text("Delivery App")
            }, navigationIcon = {
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(if (restaurant == null) "Loading..." else (restaurant as Restaurant).name, fontWeight = FontWeight.Bold)
                    AssistChip(onClick = {}, {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Star, "Rating")
                            Text("3.5")
                        }
                    })
                }

                Image(
                    painter = painterResource(id = R.drawable.dummy_image),
                    "description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.FillWidth,
                )

                Menu()
                Spacer(modifier = Modifier)
            }
        },
        bottomBar = {
            AppNavigationBar()
        },
    )
}