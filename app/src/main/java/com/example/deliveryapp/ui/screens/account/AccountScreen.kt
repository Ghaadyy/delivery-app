package com.example.deliveryapp.ui.screens.account

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.example.deliveryapp.ui.navigation.Screen
import java.time.LocalDate

data class AccountSetting(val name: String, val icon: ImageVector, val action: () -> Unit)
data class AccountSettingsSection(val name: String, val settings: List<AccountSetting>)

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    val screens = listOf(Screen.Home, Screen.Favorite, Screen.Orders, Screen.Account)
    val navController = LocalNavController.current
    val user = User(
        "Ghady", "Youssef", LocalDate.parse("2004-12-21"), "ghady@usj.edu.lb", "+961 81 385 080"
    )
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${user.firstName} ${user.lastName}", fontWeight = FontWeight.SemiBold)
                },
            )
        },
        bottomBar = {
            AppNavigationBar(navController, screens)
        },
    ) { padding ->
        val settings = listOf(
            AccountSetting(
                "Notifications", Icons.Outlined.Notifications
            ) { },
            AccountSetting(
                "Favorites", Icons.Outlined.FavoriteBorder
            ) { },
            AccountSetting("Addresses", Icons.Outlined.LocationOn) { },
        )

        val sections: List<AccountSettingsSection> = listOf(
            AccountSettingsSection("Account Details", settings)
        )

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .scrollable(state = scrollState, orientation = Orientation.Vertical),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy((16.dp)),
        ) {
            items(sections) { section ->
                SettingsSection(section)
            }
        }
    }
}

@Composable
fun SettingsSection(section: AccountSettingsSection) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardColors(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary, Color.Gray, Color.Black)
    ) {
        Text(
            section.name,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Column(modifier = Modifier.padding(16.dp)) {
            section.settings.forEachIndexed { i, setting ->
                SettingsItem(setting.name, setting.icon)
                if (i < section.settings.size - 1) HorizontalDivider(
                    modifier = Modifier.padding(
                        vertical = 16.dp
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SettingsItem(name: String, icon: ImageVector) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { }) {
        Icon(icon, name)
        Spacer(Modifier.width(16.dp))
        Text(name)
    }
}