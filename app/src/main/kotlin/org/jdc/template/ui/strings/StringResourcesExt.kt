package org.jdc.template.ui.strings

import android.app.Application
import org.jdc.template.R
import org.jdc.template.shared.model.domain.type.DisplayThemeType
import org.jdc.template.shared.model.domain.type.IndividualType

fun DisplayThemeType.toString(application: Application): String {
    val stringResId = when (this) {
        DisplayThemeType.LIGHT -> R.string.light
        DisplayThemeType.DARK -> R.string.dark
        DisplayThemeType.SYSTEM_DEFAULT -> R.string.system_default
    }

    return application.getString(stringResId)
}

fun IndividualType.getStringResId(): Int {
    return when (this) {
        IndividualType.HEAD -> R.string.head
        IndividualType.SPOUSE -> R.string.spouse
        IndividualType.CHILD -> R.string.child
        IndividualType.UNKNOWN -> R.string.unkown
    }
}
