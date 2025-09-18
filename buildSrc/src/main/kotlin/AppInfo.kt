@Suppress("MemberVisibilityCanBePrivate")

object AppInfo {
    const val APPLICATION_ID = "org.jdc.template"

    // Manifest version information
    object Version {
        private const val SEMANTIC_VERSION = "1.0.0"
        val VERSION_CODE = System.getenv("VERSION_CODE")?.toIntOrNull() ?: 1 // version code must be > 0
        val BUILD_NUMBER = System.getenv("BUILD_NUMBER") ?: 0
        val NAME = "$SEMANTIC_VERSION-($VERSION_CODE.$BUILD_NUMBER)"
    }

    object AndroidSdk {
        const val MIN = 23
        const val COMPILE = 36
        const val TARGET = COMPILE
    }
}