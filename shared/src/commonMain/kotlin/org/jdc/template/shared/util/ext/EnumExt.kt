package org.jdc.template.shared.util.ext

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String?): T? {
    name ?: return null

    return runCatching { enumValueOf<T>(name) }.getOrNull()
}

inline fun <reified T : Enum<T>> enumValueOfOrDefault(name: String?, default: T): T {
    name ?: return default

    return runCatching { enumValueOf<T>(name) }.getOrDefault(default)
}