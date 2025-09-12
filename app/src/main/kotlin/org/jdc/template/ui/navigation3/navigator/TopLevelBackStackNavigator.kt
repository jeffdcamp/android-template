package org.jdc.template.ui.navigation3.navigator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation3.navigate
import org.jdc.template.ui.navigation3.pop

class TopLevelBackStackNavigator<T : NavKey>(startKey: T) : Navigation3Navigator<T> {

    // Maintain a stack for each top level route
    private val topLevelStacks: LinkedHashMap<T, NavBackStack<T>> = linkedMapOf(
        startKey to NavBackStack(startKey)
    )

    // Expose the current top level route for consumers
    private val selectedTopLevelRoute: MutableState<T> = mutableStateOf(startKey)

    // Expose the back stack so it can be rendered by the NavDisplay
    private val backStack: NavBackStack<T> = NavBackStack(startKey)

    private fun updateBackStack(): NavBackStack<T> {
        return backStack.apply {
            clear()
            addAll(topLevelStacks.flatMap { it.value })
        }
    }

    fun navigateTopLevel(key: T) {
        // If the top level doesn't exist, add it
        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, NavBackStack(key))
        } else {
            // Otherwise just move it to the end of the stacks
            topLevelStacks.apply {
                remove(key)?.let {
                    put(key, it)
                }
            }
        }
        selectedTopLevelRoute.value = key
        updateBackStack()
    }

    override fun getBackStack(): NavBackStack<T> {
        return backStack
    }

    override fun navigate(key: T) {
        topLevelStacks[selectedTopLevelRoute.value]?.add(key)
        updateBackStack()
    }

    override fun navigate(keys: List<T>) {
        topLevelStacks[selectedTopLevelRoute.value]?.navigate(keys)
        updateBackStack()
    }

    override fun pop(): Boolean {
        return pop(null)
    }

    override fun pop(key: T?): Boolean {
        val currentStack: NavBackStack<T>? = topLevelStacks[selectedTopLevelRoute.value]
        val removedKey: T? = currentStack?.pop(key)

        // If the removed key was a top level key, remove the associated top level stack
        topLevelStacks.remove(removedKey)
        selectedTopLevelRoute.value = topLevelStacks.keys.last()
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

    override fun getSelectedTopLevelRoute(): MutableState<T>? = selectedTopLevelRoute
}
