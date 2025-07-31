package org.jdc.template.ui.strings

import android.content.Context
import org.jdc.template.R
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.shared.model.domain.type.IndividualType

fun DisplayThemeType.toString(context: Context): String {
    val stringResId = when (this) {
        DisplayThemeType.LIGHT -> R.string.light
        DisplayThemeType.DARK -> R.string.dark
        DisplayThemeType.SYSTEM_DEFAULT -> R.string.system_default
    }

    return context.getString(stringResId)
}

fun IndividualType.getStringResId(): Int {
    return when (this) {
        IndividualType.HEAD -> R.string.head
        IndividualType.SPOUSE -> R.string.spouse
        IndividualType.CHILD -> R.string.child
        IndividualType.UNKNOWN -> R.string.unkown
    }
}
