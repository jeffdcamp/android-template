package org.jdc.template.ui.util

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KotlinMathUtil {

    @Inject
    constructor()

    fun add(value1: Int, value2: Int): Int {
        return value1 + value2
    }

    fun list() = listOf(1, 2, 4)
}
