package com.example.deliveryapp.ui.screens.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val addressViewModel = (LocalContext.current as HomeActivity).addressViewModel
    val cart by cartViewModel.cart.observeAsState()
    val selectedAddress by addressViewModel.selectedAddress.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    val selectedRestaurantId by cartViewModel.selectedRestaurantId.observeAsState()

    LaunchedEffect(Unit) {
        cartViewModel.getCart()
    }

    if (isShown) {
        val total =
            cart?.sumOf { mr -> (mr.price + mr.options.sumOf { opt -> opt.price }) * mr.quantity }

        ModalBottomSheet(onDismissRequest = { isShown = false }) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                cart?.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(item.meal.name, fontSize = 18.sp)
                                    Text(
                                        "USD ${item.price * item.quantity}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            if (item.options.isNotEmpty()) Text(item.options.joinToString { opt -> opt.name })
                        }
                    }
                }
            }

            Button(
                {
                    if (cart != null && selectedRestaurantId != null && selectedAddress != null) {
                        val order = OrderRequest(
                            selectedRestaurantId!!, cart!!, selectedAddress!!.id, "Cash"
                        )
                        orderViewModel.addOrder(order)
                        coroutineScope.launch {
                            cartViewModel.clearCart()
                        }
                    }
                }, modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Text("Pay $$total and order")
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
            if (cart == null || cart!!.isEmpty()) {
                Text(
                    "Your cart is empty. Go add some items to proceed!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center).padding(horizontal = 20.dp)
                )
            } else Column(
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

            if (cart != null && cart!!.isNotEmpty()) {
                ExtendedFloatingActionButton(
                    onClick = { isShown = true },
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
}

