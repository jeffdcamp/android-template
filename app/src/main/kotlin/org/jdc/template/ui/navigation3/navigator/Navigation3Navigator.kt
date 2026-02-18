package org.jdc.template.ui.navigation3.navigator

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

abstract class Navigation3Navigator {

    // Listeners for backstack changes
    protected val onBackstackAddedListeners = mutableSetOf<(NavKey) -> Unit>()
    protected val onBackstackPoppedListeners = mutableSetOf<(NavKey) -> Unit>()

    /**
     * Returns the current back stack of the navigator.
     *
     * @return The current [NavBackStack] or null if no back stack exists.
     */
    abstract fun getCurrentBackStack(): NavBackStack<NavKey>?

    /**
     * Performs the actual navigation operation on the back stack.
     *
     * @param keys The list of keys to navigate to.
     */
    protected abstract fun doNavigate(keys: List<NavKey>)

    /**
     * Notifies listeners when the back stack changes.
     *
     * @param isAdded true if routes were added, false if routes were popped.
     */
    @Suppress("NestedBlockDepth")
    private fun onBackstackChanged(isAdded: Boolean) {
        if (isAdded) {
            if (onBackstackAddedListeners.isNotEmpty()) {
                getCurrentBackStack()?.last()?.let { currentTopKey ->
                    onBackstackAddedListeners.forEach { listener -> listener(currentTopKey) }
                }
            }
        } else {
            if (onBackstackPoppedListeners.isNotEmpty()) {
                getCurrentBackStack()?.last()?.let { currentTopKey ->
                    onBackstackPoppedListeners.forEach { listener -> listener(currentTopKey) }
                }
            }
        }
    }

    /**
     * Navigate to a new route with the given key.
     *
     * @param key The key of the route to navigate to.
     */
    fun navigate(key: NavKey) {
        navigate(listOf(key))
    }

    /**
     * Navigate to a new route with the given list of keys (add all keys to backstack).
     *
     * @param keys The list of keys to navigate to.
     */
    fun navigate(keys: List<NavKey>) {

        if (keys.isEmpty()) return

        // Delegate to the implementation
        doNavigate(keys)

        // Notify listeners with the current top NavKey on the backstack (after navigating)
        onBackstackChanged(true)
    }

    /**
     * Performs the actual pop operation on the back stack.
     *
     * @param key The key to pop to. If null, just pops the top route.
     * @return true if a route was popped, false if the back stack was empty.
     */
    protected abstract fun doPop(key: NavKey?): Boolean

    /**
     * Pop the stack till the given key is reached, or the stack is empty.
     *
     * @param key The key to pop to. If null, just pops the top route.
     * @return true if a route was popped, false if the back stack was empty.
     */
    fun pop(key: NavKey? = null): Boolean {
        // Delegate to the implementation
        val result = doPop(key)

        // Notify listeners with the current top NavKey on the backstack (after popping)
        if (result) {
            onBackstackChanged(false)
        }

        return result
    }

    /**
     * Pop the top route off the back stack and navigate to a new route with the given key.
     *
     * @param key The key of the route to navigate to.
     * @return true if a route was popped, false if the back stack was empty.
     */
    open fun popAndNavigate(key: NavKey): Boolean {
        val keyRemoved = pop()
        navigate(key)
        return keyRemoved
    }


    // ========== Support Top level navigation ==========

    /**
     * Performs the actual top-level navigation operation.
     *
     * @param key The key of the top-level route to navigate to.
     * @param reselected Whether the route is being reselected.
     */
    protected abstract fun doNavigateTopLevel(key: NavKey, reselected: Boolean)

    /**
     * Navigate to a top-level route, optionally re-selecting it if already selected.
     *
     * @param key The key of the top-level route to navigate to.
     * @param reselected Whether the route is being reselected (e.g., when already selected).
     */
    fun navigateTopLevel(key: NavKey, reselected: Boolean) {
        doNavigateTopLevel(key, reselected)

        // Notify listeners with the current top NavKey on the backstack (after navigating)
        onBackstackChanged(true)
    }

    /**
     * Get the currently selected top-level route. (This is typically used in conjunction with a navigation bar or similar UI component.)
     *
     * @return The key of the currently selected top-level route, or null if none is selected.
     */
    abstract fun getSelectedTopLevelRoute(): NavKey

    // ========== Support backstack change listeners ==========

    /**
     * Registers a listener that is notified when routes are added to the backstack.
     *
     * @param listener A callback that receives the [NavKey] of the route that was added.
     *
     * @example Using with [TopLevelBackStackNavigator]:
     * ```
     * val navigator = remember {
     *     TopLevelBackStackNavigator(navigationState).apply {
     *         addOnBackstackAddedListener { route -> }
     *     }
     * }
     * ```
     *
     * @example Using with [DefaultNavigator]:
     * ```
     * val navigator = remember {
     *     DefaultNavigator(backStack).apply {
     *         addOnBackstackAddedListener { route -> }
     *     }
     * }
     * ```
     */
    fun addOnBackstackAddedListener(listener: (NavKey) -> Unit) {
        onBackstackAddedListeners.add(listener)
    }

    /**
     * Unregisters a previously registered backstack added listener.
     *
     * @param listener The listener to remove.
     *
     * @example
     * ```
     * navigator.removeOnBackstackAddedListener(onDestinationChangedListener)
     * ```
     */
    fun removeOnBackstackAddedListener(listener: (NavKey) -> Unit) {
        onBackstackAddedListeners.remove(listener)
    }

    /**
     * Registers a listener that is notified when routes are popped from the backstack.
     *
     * @param listener A callback that receives the [NavKey] of the route that was popped.
     *
     * @example Using with [TopLevelBackStackNavigator]:
     * ```
     * val navigator = remember {
     *     TopLevelBackStackNavigator(navigationState).apply {
     *         addOnBackstackAddedListener { route -> }
     *     }
     * }
     * ```
     *
     * @example Using with [DefaultNavigator]:
     * ```
     * val navigator = remember {
     *     DefaultNavigator(backStack).apply {
     *         addOnBackstackAddedListener { route -> }
     *     }
     * }
     * ```
     */
    fun addOnBackstackPoppedListener(listener: (NavKey) -> Unit) {
        onBackstackPoppedListeners.add(listener)
    }

    /**
     * Unregisters a previously registered backstack popped listener.
     *
     * @param listener The listener to remove.
     *
     * @example
     * ```
     * navigator.removeOnBackstackPoppedListener(onDestinationChangedListener)
     * ```
     */
    fun removeOnBackstackPoppedListener(listener: (NavKey) -> Unit) {
        onBackstackPoppedListeners.remove(listener)
    }
}
