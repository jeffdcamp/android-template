@Suppress("MemberVisibilityCanBePrivate")

object AppInfo {
    const val APPLICATION_ID = "org.jdc.template"

    // Manifest version information
    object Version {
        const val CODE = 1007
        val NAME = "1.0.0 ($CODE.${System.getenv("BUILD_NUMBER")})"
    }

    object AndroidSdk {
        const val MIN = 21
        const val COMPILE = 35
        const val TARGET = COMPILE
    }
}