@Suppress("MemberVisibilityCanBePrivate")

object AppInfo {
    const val APPLICATION_ID = "org.jdc.template"

    // Manifest version information
    object Version {
        const val APP_NAME = "android-template"
        const val MIN = 1001
        val CODE = VersionCode.readVersionCode(APP_NAME, MIN)
        val NAME = "1.0.0 ($CODE.${System.getenv("BUILD_NUMBER")})"
    }

    object AndroidSdk {
        const val MIN = 21
        const val COMPILE = 31
        const val TARGET = 31
    }
}