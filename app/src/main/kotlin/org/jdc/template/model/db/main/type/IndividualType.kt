package org.jdc.template.model.db.main.type

import androidx.annotation.StringRes
import org.jdc.template.R

enum class IndividualType(@StringRes val textResId: Int) {
    UNKNOWN(R.string.unkown),
    HEAD(R.string.head),
    SPOUSE(R.string.spouse),
    CHILD(R.string.child)
}