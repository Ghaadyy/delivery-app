package com.example.deliveryapp.ui.components.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.R

@Composable
fun Option(name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = true, onCheckedChange = {})
        Text("$name ${"+ LBP 360,000 (USD 4.0)"}")
    }
}

@Composable
fun OptionSection() {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Upgrade to Combo", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Option("Fries & Pepsi")
            Option("Fries & Diet Pepsi")
            Option("Fries & 7 Up")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem() {
    var isSheetVisible by remember { mutableStateOf(false) }

    if (isSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isSheetVisible = false },
        ) {
            Column (Modifier.verticalScroll(state = rememberScrollState())) {
                Image(
                    painter = painterResource(id = R.drawable.dummy_image),
                    "description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16 / 9f),
                    contentScale = ContentScale.FillWidth,
                )
                Column (modifier = Modifier.background(Color.White).padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text("Triple Smash Burger", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Three smashed beef patties (100g), lettuce, cheddar cheese slices (4pcs) and fantastic sauce",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("LBP 1,170,000", fontWeight = FontWeight.SemiBold)
                        Text("$13.00", fontSize = 12.sp, color = Color.Gray)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Column (verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    OptionSection()
                    OptionSection()
                    OptionSection()
                }
            }
        }
    }

    Surface(onClick = { isSheetVisible = true }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text("Triple Smash Burger", fontSize = 18.sp)
                Text(
                    "Three smashed beef patties (100g), lettuce, cheddar cheese slices (4pcs) and fantastic sauce",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("LBP 1,170,000", fontWeight = FontWeight.Bold)
                    Text("$13.00", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Image(
                painter = painterResource(id = R.drawable.dummy_image),
                "description",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Red),
            )
        }
    }
}