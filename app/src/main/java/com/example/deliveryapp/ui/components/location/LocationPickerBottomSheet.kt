package com.example.deliveryapp.ui.components.location

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.HomeActivity
import com.example.deliveryapp.data.model.Address

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPickerBottomSheet(
    addresses: List<Address>,
    onSelect: (address: Address) -> Unit,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    val addressViewModel = (LocalContext.current as HomeActivity).addressViewModel
    val selectedAddress by addressViewModel.selectedAddress.observeAsState()

    ModalBottomSheet(onDismissRequest = onDismissRequest) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                "Where should we deliver to?", fontWeight = FontWeight.Bold, fontSize = 20.sp
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
            ) {
                addresses.forEach { addr ->
                    Surface(
                        { onSelect(addr) }, modifier = Modifier
                            .fillMaxWidth()
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                    ) {
                        Row(
                            Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val isTinted =
                                selectedAddress != null && addr.id == selectedAddress!!.id
                            val selectedColor =
                                if (isTinted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                            Icon(
                                Icons.Filled.Home,
                                "Home",
                                tint = selectedColor,
                                modifier = Modifier.weight(.1f)
                            )
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(.9f)
                            ) {
                                Text(
                                    "Lat: ${addr.latitude} Long: ${addr.longitude}",
                                    fontWeight = FontWeight.Bold,
                                    color = selectedColor
                                )
                                Text(
                                    if (addr.street == "" || addr.street == null) "Street not available" else addr.street
                                )
                                Text(
                                    if (addr.building == "" || addr.building == null || addr.apartment == "" || addr.apartment == null)
                                        "Apartment not available"
                                    else "${addr.building}, ${addr.apartment}"
                                )
                            }
                        }
                    }
                }
            }

            Button(onClick = onClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Text("Add New Address")
            }
        }
    }
}