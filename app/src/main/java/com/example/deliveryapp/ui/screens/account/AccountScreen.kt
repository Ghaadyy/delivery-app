package com.example.deliveryapp.ui.screens.account

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.deliveryapp.AuthenticationActivity
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.data.model.JsonPatch
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

data class AccountSetting(val name: String, val icon: ImageVector, val action: () -> Unit)
data class AccountSettingsSection(val name: String, val settings: List<AccountSetting>)

//fun convertMillisToDate(millis: Long): String {
//    val formatter = SimpleDateFormat("MM/dd/yyyy", LocalDate.())
//    return formatter.format(Date(millis))
//}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountScreen() {
    val userViewModel = (LocalContext.current as HomeActivity).userViewModel

    val user by userViewModel.user.observeAsState()
    val error by userViewModel.error.observeAsState()
    val ctx = LocalContext.current
    val activity = LocalContext.current as HomeActivity

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf<String?>(null) }
    val dateState = rememberDatePickerState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val displayDate = selectedDate?.format(dateFormatter) ?: "Select a Date"

    LaunchedEffect(Unit) {
        userViewModel.fetchUserInfo()
        Log.d("AccountScreen", user.toString())
    }

    LaunchedEffect(user) {
        if(user != null) {
            firstName = user!!.firstName
            lastName = user!!.lastName
            email = user!!.email
            phoneNumber = user!!.phoneNumber
//            selectedDate = user!!.dob?.let {
//                LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
//            }
        }
    }

    LaunchedEffect(error) {
        error?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, withDismissAction = true)
                userViewModel.clearError()
            }
        }
    }


    val scrollState = rememberScrollState()

    if (user == null) return Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text("Account", fontWeight = FontWeight.SemiBold)
                },
            )
        },
        bottomBar = { AppNavigationBar() },
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Text("Loading...")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text("${user!!.firstName} ${user!!.lastName}", fontWeight = FontWeight.SemiBold)
                },
            )
        },
        bottomBar = { AppNavigationBar() },
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
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = phoneNumber ?: "",
                        onValueChange = { phoneNumber = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextField(
                        value = displayDate,
                        onValueChange = {},
                        label = { Text("Date of Birth") },
                        readOnly = true,
                        enabled = false,
                        colors = TextFieldDefaults.colors(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showDialog = true
                            }
                    )

                    if (showDialog) {
                        DatePickerDialog(
                            onDismissRequest = { showDialog = false },
                            confirmButton = {
//                                selectedDate = dateState.selectedDateMillis?.let {
//                                    convertMillisToDate(it)
//                                }
                            }
                        ) {
                            DatePicker(dateState)
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button({
                            val userPatch = listOf(
                                JsonPatch("replace", "/firstName", firstName),
                                JsonPatch("replace", "/lastName", lastName),
//                                JsonPatch("replace", "/dob", user!!.dob),
                                JsonPatch("replace", "/phoneNUmber", phoneNumber),
                            )

                            userViewModel.update(userPatch)
                        }) {
                            Text("Save changes")
                        }
                        Button({
                            val intent = Intent(activity, AuthenticationActivity::class.java)

                            activity.startActivity(intent)
                            activity.finish()
                        }, colors = ButtonColors(MaterialTheme.colorScheme.error, MaterialTheme.colorScheme.onError, Color.Black, Color.Black)) {
                            Text("Log out")
                        }
                    }
                }
            }
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
            .padding(horizontal = 16.dp), colors = CardColors(
            MaterialTheme.colorScheme.primaryContainer,
            MaterialTheme.colorScheme.primary,
            Color.Gray,
            Color.Black
        )
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
                    ), color = MaterialTheme.colorScheme.primary
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