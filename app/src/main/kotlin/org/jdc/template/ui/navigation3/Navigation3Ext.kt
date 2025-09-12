package org.jdc.template.ui.navigation3

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun <T: NavKey> NavBackStack<T>.navigate(key: T) {
    add(key)
}

fun <T: NavKey> NavBackStack<T>.navigate(key: List<T>) {
    addAll(key)
}

fun <T: NavKey> NavBackStack<T>.popAndNavigate(key: T, popToKey: T? = null): T? {
    val lastPoppedKey = pop(popToKey)
    navigate(key)

    return lastPoppedKey
}

fun <T: NavKey> NavBackStack<T>.pop(popToKey: T? = null): T? {
    return when (popToKey) {
        null -> {
            removeLastOrNull() != null
            null
        }
        else -> {
            var lastPoppedKey: T? = null
            var popCount = 0
            while (
                last() != popToKey &&  // don't pop if we are already on the popToKey
                size != 1 // don't pop the last item (NavDisplay will crash if there are 0 items)
            ) {
                lastPoppedKey = removeLastOrNull()
                popCount++
            }

            popCount != 0 || lastPoppedKey != null
            lastPoppedKey
        }
    }
}
