package org.jdc.template.ui.compose

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.Composable
import androidx.compose.Recomposer
import androidx.fragment.app.Fragment
import androidx.ui.core.setContent
import kotlin.random.Random

fun Fragment.setContent(content: @Composable() () -> Unit): View {
    return FrameLayout(requireContext()).apply {
        id = Random.nextInt()
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContent(Recomposer.current()) {
            content()
        }
    }
}