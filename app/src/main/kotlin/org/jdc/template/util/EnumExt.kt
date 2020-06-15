package org.jdc.template.util

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String?): T? {
    name ?: return null

    return try {
        enumValueOf<T>(name)
    } catch (_: Exception) {
        null
    }
}

inline fun <reified T : Enum<T>> enumValueOfOrDefault(name: String?, default: T): T {
    name ?: return default

    return try {
        enumValueOf(name)
    } catch (_: IllegalArgumentException) {
        default
    }
}