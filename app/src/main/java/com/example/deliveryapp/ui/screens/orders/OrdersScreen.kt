package com.example.deliveryapp.ui.screens.orders

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.R
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.fragments.order.OrderListFragment
import kotlinx.coroutines.launch

@Composable
fun OrdersScreen(
    fragmentManager: FragmentManager
) {
    val orderViewModel = (LocalContext.current as HomeActivity).orderViewModel
    val errorMessage by orderViewModel.errorMessage.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, withDismissAction = true)
                orderViewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            AndroidView(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
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
