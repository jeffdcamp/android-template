@file:Suppress("unused")

package org.jdc.template.ui.compose.form

import kotlinx.coroutines.flow.MutableStateFlow

object FieldValidation {
    private fun checkFieldNotBlank(textFlow: MutableStateFlow<TextFieldData>, failMessage: String): Boolean {
        if (textFlow.value.text.isBlank()) {
            textFlow.value = textFlow.value.copy(isError = true, supportingText = failMessage)
            return false
        }

        return true
    }

    private fun checkFieldValue(textFlow: MutableStateFlow<TextFieldData>, failMessage: String, validation: (String) -> Boolean): Boolean {
        if (!validation(textFlow.value.text)) {
            textFlow.value = textFlow.value.copy(isError = true, supportingText = failMessage)
            return false
        }

        return true
    }
}