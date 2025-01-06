package com.example.deliveryapp.ui.screens.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
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
import com.example.deliveryapp.data.model.OrderRequest
import com.example.deliveryapp.ui.components.cart.MealRequestItem
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object CartPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(orderViewModel: OrderViewModel = viewModel()) {
    val navController = LocalNavController.current
    val cartViewModel = (LocalContext.current as HomeActivity).cartViewModel
    val cart by cartViewModel.cart.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    val userViewModel = (LocalContext.current as HomeActivity).userViewModel
    val token by userViewModel.token.observeAsState()
    val selectedRestaurantId by cartViewModel.selectedRestaurantId.observeAsState()

    LaunchedEffect(Unit) {
        cartViewModel.getCart()
    }

    if (isShown) {
        ModalBottomSheet(onDismissRequest = { isShown = false }) {
            Button({
                if (cart != null && selectedRestaurantId != null) {
                    val order = OrderRequest(
                        selectedRestaurantId!!, cart!!, 1, "Cash"
                    )
                    orderViewModel.addOrder(token!!.token, order)
                    coroutineScope.launch {
                        cartViewModel.clearCart()
                    }
                }
            }) {
                Text("Pay and order")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Cart", fontWeight = FontWeight.Bold) }, navigationIcon = {
                Icon(Icons.AutoMirrored.Filled.ArrowBack,
                    "Back",
                    modifier = Modifier.clickable { navController.popBackStack() })
            })
        },
        bottomBar = { AppNavigationBar() },
    ) { padding ->
        Box(
            Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                cart?.forEach { item ->
                    MealRequestItem(item, onUpdate = { updatedQuantity ->
                        coroutineScope.launch {
                            cartViewModel.updateItem(item.copy(quantity = updatedQuantity))
                        }
                    }) {
                        coroutineScope.launch {
                            cartViewModel.removeFromCart(item)
                        }
                    }
                }
            }

            ExtendedFloatingActionButton(onClick = { isShown = true },
                text = { Text("Go to checkout") },
                icon = { Icon(Icons.Filled.ShoppingCart, "Go to checkout") },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
            )
        }
    }
}

