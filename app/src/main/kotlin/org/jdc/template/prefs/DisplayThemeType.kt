package org.jdc.template.prefs

import org.jdc.template.R

enum class DisplayThemeType
constructor(
    val stringResId: Int
) {
    SYSTEM_DEFAULT(R.string.system_default),
    LIGHT(R.string.light),
    DARK(R.string.dark);
}
