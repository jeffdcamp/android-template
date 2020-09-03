package org.jdc.template.ui.compose

import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

fun Fragment.setContent(content: @Composable () -> Unit): View {
    return ComposeView(requireContext()).apply {
        setContent(content)
    }
}