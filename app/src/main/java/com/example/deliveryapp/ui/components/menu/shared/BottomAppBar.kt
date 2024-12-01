package com.example.deliveryapp.ui.components.menu.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomAppBar() {
    androidx.compose.material3.BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            IconButton(onClick = {}, enabled = true) {
                Icon(
                    Icons.Filled.Home,
                    "Home",
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Search,
                    "Browse",
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    Icons.Filled.Settings,
                    "Settings",
                )
            }
        }
    }
}