package com.example.deliveryapp.ui.screens.orders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.deliveryapp.R
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.fragments.order.OrderListFragment

@Composable
fun OrdersScreen(
    fragmentManager: FragmentManager
) {
    Scaffold(
        content = { paddingValues ->
            AndroidView(
                modifier = Modifier.padding(paddingValues).fillMaxSize(),
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
        },
        bottomBar = { AppNavigationBar() }
    )
}
