package org.jdc.template.inject

object TestInjector {
    private lateinit var testComponent: TestComponent

    @JvmStatic
    @Synchronized
    fun init() {
        testComponent = DaggerTestComponent.builder()
                .build()
    }

    @JvmStatic
    @Synchronized
    fun get() = testComponent
}