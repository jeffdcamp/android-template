package org.jdc.template.ui.navigation3.navigator

import androidx.compose.runtime.MutableState
import androidx.navigation3.runtime.NavKey

interface Navigation3Navigator<T: NavKey> {
    /**
     * Navigate to a new route with the given key.
     *
     * @param key The key of the route to navigate to.
     */
    fun navigate(key: T)

    /**
     * Navigate to a new route with the given list of keys (add all keys to backstack).
     *
     * @param keys The list of keys to navigate to.
     */
    fun navigate(keys: List<T>)

    /**
     * Pop the top route off the back stack.
     *
     * @return true if a route was popped, false if the back stack was empty.
     */
    fun pop(): Boolean

    /**
     * Pop the stack till the given key is reached, or the stack is empty.
     *
     * @param key The key to pop to. If null, just pops the top route.
     * @return true if a route was popped, false if the back stack was empty.
     */
    fun pop(key: T?): Boolean

    /**
     * Pop the top route off the back stack and navigate to a new route with the given key.
     *
     * @param key The key of the route to navigate to.
     * @return true if a route was popped, false if the back stack was empty.
     */
    fun popAndNavigate(key: T): Boolean

    /**
     * Get the backstack (especially for the NavDisplay)
     *
     * @return A list of keys representing the current back stack.
     */
    fun getBackStack(): List<T>


    // ========== Support Top level navigation ==========

    /**
     * Navigate to a top-level route, optionally re-selecting it if already selected.
     *
     * @param key The key of the top-level route to navigate to.
     * @param reselected Whether the route is being reselected (e.g., when already selected).
     */
    fun navigateTopLevel(key: T, reselected: Boolean)

    /**
     * Get the currently selected top-level route. (This is typically used in conjunction with a navigation bar or similar UI component.)
     *
     * @return The key of the currently selected top-level route, or null if none is selected.
     */
    fun getSelectedTopLevelRoute(): MutableState<T>?
}
