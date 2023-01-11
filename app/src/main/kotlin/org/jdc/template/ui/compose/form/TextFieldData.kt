package org.jdc.template.ui.compose.form

data class TextFieldData(
    val text: String,
    val helperText: String? = null,
    val isError: Boolean = false,
    val errorHelperText: String? = null,
)
