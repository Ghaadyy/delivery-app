package com.example.deliveryapp.ui.screens.address

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.LocalNavController
import com.example.deliveryapp.data.model.Address
import com.example.deliveryapp.ui.components.shared.AppNavigationBar
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data object CreateAddressPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAddressScreen() {
    val navController = LocalNavController.current
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val addressViewModel = (context as HomeActivity).addressViewModel

    val error by addressViewModel.error.observeAsState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var street by remember { mutableStateOf("") }
    var apartment by remember { mutableStateOf("") }
    var building by remember { mutableStateOf("") }
    var location by remember { mutableStateOf<Location?>(null) }
    val hasLocationPermission = remember {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
    val permissionState = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
//            if (isGranted) {
//                Toast.makeText(context, "Location Permission Granted", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "Location Permission Denied", Toast.LENGTH_SHORT).show()
//            }
    }

    LaunchedEffect(Unit) {
        if (hasLocationPermission) {
            fusedLocationClient.lastLocation.addOnSuccessListener { loc ->
                location = loc
            }
        } else {
            permissionState.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    LaunchedEffect(error) {
        error?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, withDismissAction = true)
                addressViewModel.clearError()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Create New Address", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "Back",
                        modifier = Modifier.clickable { navController.popBackStack() })
                })
        },
        bottomBar = { AppNavigationBar() },
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                TextField(
                    value = if(location?.latitude == null) "" else location!!.latitude.toString(),
                    onValueChange = { street = it },
                    label = { Text("Latitude") },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
                TextField(
                    value = if(location?.longitude == null) "" else location!!.longitude.toString(),
                    onValueChange = { street = it },
                    label = { Text("Longitude") },
                    readOnly = true,
                    modifier = Modifier.weight(1f)
                )
            }

            TextField(
                value = street,
                onValueChange = { street = it },
                label = { Text("Street") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = building,
                onValueChange = { building = it },
                label = { Text("Building") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = apartment,
                onValueChange = { apartment = it },
                label = { Text("Apartment") },
                modifier = Modifier.fillMaxWidth()
            )

            Button({
                if(location != null) {
                    val address = Address(
                        0,
                        0,
                        location!!.latitude,
                        location!!.longitude,
                        street,
                        apartment,
                        building
                    )
                    addressViewModel.addAddress(address)
                }
            }, Modifier.fillMaxWidth()) {
                Text("Create Address")
            }
        }
    }
}

