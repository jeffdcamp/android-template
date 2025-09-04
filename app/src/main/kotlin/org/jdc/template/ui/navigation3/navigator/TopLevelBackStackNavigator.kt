package org.jdc.template.ui.navigation3.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation3.navigate
import org.jdc.template.ui.navigation3.pop

class TopLevelBackStackNavigator<T : NavKey>(startKey: T) : Navigation3Navigator<T> {

    // Maintain a stack for each top level route
    private val topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    // Expose the current top level route for consumers
    private var _selectedTopLevelRoute by mutableStateOf(startKey)

    // Expose the back stack so it can be rendered by the NavDisplay
    private val backStack: SnapshotStateList<T> = mutableStateListOf(startKey)

    private fun updateBackStack(): SnapshotStateList<T> {
        return backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }
    }

    fun navigateTopLevel(key: T) {
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        _selectedTopLevelRoute = key
        updateBackStack()
    }

    override fun getBackStack(): SnapshotStateList<T> {
        return backStack
    }

    override fun navigate(key: T) {
        topLevelStacks[_selectedTopLevelRoute]?.add(key)
        updateBackStack()
    }

    override fun navigate(keys: List<T>) {
        topLevelStacks[_selectedTopLevelRoute]?.navigate(keys)
        updateBackStack()
    }

    override fun pop(): Boolean {
        return pop(null)
    }

    override fun pop(key: T?): Boolean {
        val currentStack: SnapshotStateList<T>? = topLevelStacks[_selectedTopLevelRoute]
        val removedKey: T? = currentStack?.pop(key)

        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        _selectedTopLevelRoute = topLevelStacks.keys.last()
        updateBackStack()
        return removedKey != null
    }

    override fun popAndNavigate(key: T): Boolean {
        val keyRemoved = pop()
        navigate(key)

        return keyRemoved
    }

    override fun navigateTopLevel(key: T, reselected: Boolean) {
        if (reselected) {
            // clear back stack
            backStack.pop(popToKey = key)
        } else {
            navigateTopLevel(key)
        }
    }

    override fun getSelectedTopLevelRoute(): T? = _selectedTopLevelRoute
}
