package org.jdc.template

import org.mockito.Mockito

object MockitoKotlinHelper {
    // Helper to handle Non-null any()  (Mockito does not handle Kotlin non-null objects with any())
    // https://medium.com/@elye.project/befriending-kotlin-and-mockito-1c2e7b0ef791#.10oes9nnp
    // Example: MockitoKotlinHelper.any<URI>()
    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> uninitialized(): T = null as T
}