@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package org.jdc.template.ui.compose

/**
 * Use to accommodate hoisting of a value and an action to change it
 */
class Hoisted<T>(val value: T, val onChange: (T) -> Unit) {
    /**
     * Send original value as the action
     */
    fun onAction() = onChange(value)
}

/**
 * Use to accommodate hoisting of a list of values, each with a respective choice action
 */
class HoistedList<T>(val values: List<T>, val onChoice: (T) -> Unit) {

    /**
     * Ths size of the list
     */
    val size = values.size

    /**
     * True if empty
     */
    fun isEmpty(): Boolean = values.isEmpty()

    /**
     * Get an item
     */
    operator fun get(index: Int) = values[index]

    /**
     * Hoist an item by its index
     */
    fun hoist(index: Int): Hoisted<T> = Hoisted(values[index], onChoice)
}

/**
 * Use to accommodate hoisting of a value and an action to change it
 */
fun <T> T.hoist(onChange: (T) -> Unit) = Hoisted(this, onChange)

/**
 * Use to accommodate hoisting of a list of values, each with a respective choice action
 */
fun <T> List<T>.hoistList(onChoice: (T) -> Unit) = HoistedList(this, onChoice)

/**
 * Call [action] with each element hoisted
 */
inline fun <T> HoistedList<T>.hoistEach(action: (Hoisted<T>) -> Unit) {
    values.forEach {
        action(Hoisted(it, onChoice))
    }
}

/**
 * Call [action] with each index and element hoisted
 */
inline fun <T> HoistedList<T>.hoistEachIndexed(action: (Int, Hoisted<T>) -> Unit) {
    values.forEachIndexed { index, item ->
        action(index, Hoisted(item, onChoice))
    }
}