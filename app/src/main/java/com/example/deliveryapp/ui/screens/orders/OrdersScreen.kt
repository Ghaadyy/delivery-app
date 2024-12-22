package com.example.deliveryapp.ui.screens.orders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.R
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.fragments.order.OrderListFragment
import com.example.deliveryapp.ui.navigation.Screen
import com.example.deliveryapp.ui.viewModel.OrderViewModel


@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    fragmentManager: FragmentManager,
    ordersViewModel: OrderViewModel = viewModel()
) {
    val screens = listOf(Screen.Home, Screen.Favorite)
    LaunchedEffect(Unit) {
        ordersViewModel.getOrders()
    }

    Scaffold(
        content = { paddingValues ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                AndroidView(
                    modifier = Modifier,
                    factory = {
                        context ->
                        val frgId = FragmentContainerView(context).apply {
                            id = R.id.fragment_container
                        }
                        val transaction = fragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment_container, OrderListFragment())
                        transaction.addToBackStack(null)
                        transaction.commit()
                        frgId
                    },
                    update = {}
                )
            }
        },
        bottomBar = {
            AppNavigationBar(LocalNavController.current, screens)
        }
    )
}
