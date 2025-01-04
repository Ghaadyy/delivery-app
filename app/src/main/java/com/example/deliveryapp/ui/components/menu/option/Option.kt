package com.example.deliveryapp.ui.components.menu.option

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.deliveryapp.data.model.menu.MealOption

@Composable
fun OptionSingle(option: MealOption, selected: Boolean, onSelect: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = {
            onSelect(!selected)
        })

        OptionText(option)
    }
}

@Composable
fun OptionMultiple(option: MealOption, checked: Boolean, onChange: (checked: Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = checked, onCheckedChange = { isChecked ->
            onChange(isChecked)
        })
        OptionText(option)
    }
}

@Composable
private fun OptionText(option: MealOption) {
    Text("${option.name} + LBP ${option.price * 89500} ($ ${option.price})")
}