package org.jdc.template.ux.main

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.vector.ImageVector
import org.jdc.template.R
import org.jdc.template.ui.compose.icons.google.outlined.People

enum class NavBarItem(
    val imageVector: ImageVector,
    @StringRes val textResId: Int? = null,
) {
    PEOPLE(Icons.Outlined.People, R.string.people),
    ABOUT(Icons.Outlined.Info, R.string.about);
}