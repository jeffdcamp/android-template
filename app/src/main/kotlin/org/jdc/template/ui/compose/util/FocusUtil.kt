package org.jdc.template.ui.compose.util

import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isShiftPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

fun formKeyEventHandler(
    keyEvent: KeyEvent,
    focusManager: FocusManager,
    inputFilterKeys: List<Key> = listOf(Key.Tab, Key.Enter),
    onEnter: (() -> Unit)? = null
): Boolean {
    // prevent character in from being placed in the TextField (default Tab / Enter)
    if (keyEvent.type == KeyEventType.KeyDown && inputFilterKeys.contains(keyEvent.key)) {
        return true
    }

    // prevent double calls (only handle UP action)
    if (keyEvent.type != KeyEventType.KeyUp) {
        return false
    }

    return when {
        keyEvent.key == Key.Tab -> {
            focusManager.moveFocus(FocusDirection.Next)
            true
        }
        keyEvent.key == Key.DirectionDown -> {
            focusManager.moveFocus(FocusDirection.Down)
            true
        }
        keyEvent.key == Key.Tab && keyEvent.isShiftPressed -> {
            focusManager.moveFocus(FocusDirection.Previous)
            true
        }
        keyEvent.key == Key.DirectionUp -> {
            focusManager.moveFocus(FocusDirection.Up)
            true
        }
        keyEvent.key == Key.Enter -> {
            if (onEnter != null) {
                onEnter()
                true
            } else {
                false
            }
        }
        else -> {
            false
        }
    }
}
