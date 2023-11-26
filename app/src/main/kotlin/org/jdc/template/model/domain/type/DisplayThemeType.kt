package org.jdc.template.model.domain.type

import android.content.Context
import org.jdc.template.R

enum class DisplayThemeType(private val textResId: Int) {
    SYSTEM_DEFAULT(R.string.system_default),
    LIGHT(R.string.light),
    DARK(R.string.dark);

    fun getString(context: Context): String {
        return context.getText(this.textResId).toString()
    }
}
