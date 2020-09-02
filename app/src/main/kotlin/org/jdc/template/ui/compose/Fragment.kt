package org.jdc.template.ui.compose

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Recomposer
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.setContent
import androidx.fragment.app.Fragment

fun Fragment.setContent(content: @Composable () -> Unit): View {
    return ComposeView(requireContext()).apply {
        setContent(Recomposer.current()) {
            content()
        }
    }
}