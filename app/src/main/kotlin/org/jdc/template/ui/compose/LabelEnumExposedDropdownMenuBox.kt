package org.jdc.template.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T: Enum<T>> LabelEnumExposedDropdownMenuBox(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    optionToText: @Composable (T) -> String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var currentSelectedOption by remember { mutableStateOf(selectedOption) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {  },
        modifier = modifier
    ) {
        TextButton(
            onClick = { expanded = !expanded },
        ) {
            Row {
                Text(
                    optionToText(currentSelectedOption),
                    Modifier.align(Alignment.CenterVertically)
                )
                Box(
                    Modifier.align(Alignment.CenterVertically)
                ) {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                }
            }
        }
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(optionToText(option)) },
                    onClick = {
                        currentSelectedOption = option
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}