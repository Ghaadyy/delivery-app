package com.example.deliveryapp.ui.components.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppNavigationBar() {
    NavigationBar {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            NavigationBarItem(selected = true, onClick = {}, icon = {
                Icon(
                    Icons.Filled.Home,
                    "Home",
                )
            }, label = { Text("Home") })
            NavigationBarItem(selected = false, onClick = {}, icon = {
                Icon(
                    Icons.Filled.Search,
                    "Browse",
                )
            }, label = { Text("Browse") })
            NavigationBarItem(selected = false, onClick = {}, icon = {
                Icon(
                    Icons.Filled.ShoppingCart,
                    "Orders",
                )
            }, label = { Text("Orders") })
            NavigationBarItem(selected = false, onClick = {}, icon = {
                Icon(
                    Icons.Filled.Settings,
                    "Settings",
                )
            }, label = { Text("Settings") })
        }
    }
}