package org.jdc.template.ui.navigation

import android.os.Bundle
import androidx.navigation.NavType

object ListNavType {
    class StringListType(private val separator: String = RouteUtil.DEFAULT_STRING_LIST_DELIMITER): NavType<List<String>?>(true) {
        override fun parseValue(value: String): List<String>? {
            return value.split(separator)
        }

        override fun get(bundle: Bundle, key: String): List<String>? {
            return (bundle[key] as List<String>?)
        }

        override fun put(bundle: Bundle, key: String, value: List<String>?) {
            bundle.putStringArrayList(key, value?.let { ArrayList(it) } )
        }
    }
}