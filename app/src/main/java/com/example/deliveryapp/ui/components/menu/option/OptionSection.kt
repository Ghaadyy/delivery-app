package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.deliveryapp.data.model.menu.MealOption
import com.example.deliveryapp.data.model.menu.MealUpgrade

@Composable
fun OptionSection(upgrade: MealUpgrade, onChange: (optionId: MealOption?, optionIds: List<MealOption>) -> Unit) {
    var selectedOptions by remember { mutableStateOf<List<MealOption>>(listOf()) }
    var selectedOption by remember { mutableStateOf<MealOption?>(null) }

    Column(
        Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(upgrade.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            upgrade.options.forEach { opt ->
                if(upgrade.multiple) {
                    OptionSingle(opt, selectedOption == opt) { selected ->
                        selectedOption = if(selected) opt else null
                        onChange(selectedOption, selectedOptions)
                    }
                } else {
                    OptionMultiple(opt, selectedOptions.contains(opt)) { checked ->
                        selectedOptions = if(checked) {
                            selectedOptions.plus(opt)
                        } else {
                            selectedOptions.filter { it != opt }
                        }
                        onChange(selectedOption, selectedOptions)
                    }
                }
            }
        }
    }
}