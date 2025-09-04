package org.jdc.template.ui.navigation3.navigator

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import org.jdc.template.ui.navigation3.navigate
import org.jdc.template.ui.navigation3.pop

class DefaultNavigator<T : NavKey>(startKey: T) : Navigation3Navigator<T> {
    private val backStack: SnapshotStateList<T> = mutableStateListOf(startKey)

    override fun getBackStack(): List<T> {
        return backStack.toList()
    }

    override fun navigate(key: T) {
        backStack.navigate(key)
    }

    override fun navigate(keys: List<T>) {
        backStack.navigate(keys)
    }

    override fun pop(): Boolean {
        return pop(null)
    }

    override fun pop(key: T?): Boolean {
        return backStack.pop(key) != null
    }

    override fun popAndNavigate(key: T): Boolean {
        val keyRemoved = pop()
        navigate(key)

        return keyRemoved
    }

    override fun navigateTopLevel(key: T, reselected: Boolean) {
        error("navigateTopLevel() navigation not implemented in DefaultNavigator (use TopLevelBackstackNavigator instead)")
    }

    override fun getSelectedTopLevelRoute(): T? {
        error("getSelectedTopLevelRoute() not implemented in DefaultNavigator (use TopLevelBackstackNavigator instead)")
    }
}