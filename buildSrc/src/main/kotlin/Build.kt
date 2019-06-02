@file:Suppress("MemberVisibilityCanBePrivate")
const val KOTLIN_VERSION = "1.3.31"
const val ANDROIDX_NAVIGATION_VERSION = "2.0.0"
const val PLAYSERVICE_LICENSE_VERSION = "0.9.1"

object AndroidSdk {
    const val MIN = 21
    const val COMPILE = 28
    const val TARGET = COMPILE
}

object Libs {
    private object Versions {
        const val DAGGER = "2.23"
        const val ROOM = "2.1.0-rc01"
        const val DBTOOLS = "4.9.0"
        const val OKHTTP = "3.14.2"
        const val MATERIAL_DIALOGS = "2.8.1"
        const val COROUTINES = "1.2.1"

        // Test
        const val JUNIT = "5.4.1"
        const val ESPRESSO = "3.2.0"
    }

    // Android (https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/buildSrc/src/main/kotlin/androidx/build/dependencies/Dependencies.kt)
    const val ANDROIDX_ANNOTATIONS = "androidx.annotation:annotation:1.0.2"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.1.0-alpha05"
    const val ANDROIDX_RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.0.0"
    const val ANDROIDX_PREFERENCE = "androidx.preference:preference-ktx:1.0.0"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.0.2"

    const val ARCH_LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:2.0.0"
    const val ARCH_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime:2.0.0"
    const val ARCH_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0"
    const val ARCH_LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:2.0.0"

    const val ARCH_NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$ANDROIDX_NAVIGATION_VERSION"
    const val ARCH_NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$ANDROIDX_NAVIGATION_VERSION"

    const val ARCH_WORK_RUNTIME = "androidx.work:work-runtime-ktx:2.0.1"

    const val ANDROID_MATERIAL = "com.google.android.material:material:1.1.0-alpha06"
    const val ANDROID_MULTIDEX = "androidx.multidex:multidex:2.0.1"
    const val ANDROID_MULTIDEX_INSTRUMENTATION = "androidx.multidex:multidex-instrumentation:2.0.0"

    const val PLAYSERVICE_ANALYTICS = "com.google.android.gms:play-services-analytics:16.0.8"
    const val PLAYSERVICE_LICENSES = "com.google.android.gms:play-services-oss-licenses:$PLAYSERVICE_LICENSE_VERSION"


    // Code
    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.11.0"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}"
    const val DAGGER = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    const val KOTLIN_RETROFIT_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0"
    const val EXTRAS_DELEGATES = "me.eugeniomarletti:android-extras-delegates:1.0.5"
    const val THREETEN_ABP = "com.jakewharton.threetenabp:threetenabp:1.2.0"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
    const val VIEWMODEL_INJECT = "com.vikingsen.inject:viewmodel-inject:0.1.1"
    const val VIEWMODEL_INJECT_PROCESSOR = "com.vikingsen.inject:viewmodel-inject-processor:0.1.1"

    // UI
    const val MATERIAL_DIALOGS_CORE = "com.afollestad.material-dialogs:core:${Versions.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_DATETIME = "com.afollestad.material-dialogs:datetime:${Versions.MATERIAL_DIALOGS}"
    const val MATERIAL_DIALOGS_LIFECYCLE = "com.afollestad.material-dialogs:lifecycle:${Versions.MATERIAL_DIALOGS}"

    // Database
    const val ARCH_ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ARCH_ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ARCH_ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val DBTOOLS_ROOM = "org.dbtools:dbtools-room:${Versions.DBTOOLS}"
    const val DBTOOLS_ROOM_SQLITE = "org.dbtools:dbtools-room-sqliteorg:${Versions.DBTOOLS}"

    // Network
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"

    // Test

    const val TEST_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
    const val TEST_ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:${Versions.ESPRESSO}"
    const val TEST_RUNNER = "androidx.test:runner:1.2.0"
    const val TEST_RULES = "androidx.test:rules:1.2.0"
    const val TEST_ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.1"
    const val TEST_JUNIT = "org.junit.jupiter:junit-jupiter:${Versions.JUNIT}"
    const val TEST_JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${Versions.JUNIT}"
    const val TEST_OKHTTP_MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}"
    const val TEST_MOCKITO_CORE = "org.mockito:mockito-core:2.28.2"
    const val TEST_THREETENBP = "org.threeten:threetenbp:1.4.0"
    const val TEST_XERIAL_SQLITE = "org.xerial:sqlite-jdbc:3.27.2.1"
    const val TEST_ARCH_ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"
    const val TEST_DBTOOLS_ROOM_JDBC = "org.dbtools:dbtools-room-jdbc:${Versions.DBTOOLS}"
    const val TEST_KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}"
    const val TEST_MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
}