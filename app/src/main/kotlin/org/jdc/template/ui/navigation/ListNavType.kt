package org.jdc.template.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType

/**
 * Support List of Strings in Jetpack Navigation
 *
 * Example:
 *      override fun <D : NavDestination> NavDestinationBuilder<D>.setupNav() {
 *          argument(Arg.ITEM_IDS) {
 *              type = ListNavType.StringListType()
 *          }
 *      }
 */
object ListNavType {
    class StringListType(private val separator: String = RouteUtil.DEFAULT_STRING_LIST_DELIMITER) : NavType<List<String>?>(true) {
        override fun parseValue(value: String): List<String> {
            return value.split(separator)
        }

        override fun get(bundle: Bundle, key: String): List<String>? {
            return bundle.getStringArrayList(key)
        }

        override fun put(bundle: Bundle, key: String, value: List<String>?) {
            bundle.putStringArrayList(key, value?.let { ArrayList(it) })
        }
    }
}