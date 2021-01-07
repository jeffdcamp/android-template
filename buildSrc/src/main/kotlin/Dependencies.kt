// Versions for Dependencies AND Build Plugins
const val KOTLIN_VERSION = "1.4.21"
private const val COROUTINES_VERSION = "1.4.2"
const val DAGGER_HILT_VERSION = "2.30.1-alpha"
const val ANDROIDX_NAVIGATION_VERSION = "2.3.2"

object BuildDeps {
    const val ANDROID = "com.android.tools.build:gradle:4.2.0-beta03"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlin:kotlin-serialization:$KOTLIN_VERSION"
    const val GOOGLE_SERVICES = "com.google.gms:google-services:4.3.4"
    const val HILT = "com.google.dagger:hilt-android-gradle-plugin:$DAGGER_HILT_VERSION"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-gradle:2.4.1"
    const val FIREBASE_APP_DISTRIBUTION = "com.google.firebase:firebase-appdistribution-gradle:2.0.1"
    const val FIREBASE_PERF = "com.google.firebase:perf-plugin:1.3.4"
    const val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2"
    const val GRADLE_VERSIONS = "com.github.ben-manes:gradle-versions-plugin:0.36.0"
    const val PLAY_PUBLISHER = "com.github.triplet.gradle:play-publisher:3.1.0-agp4.2-2"
    const val LICENSE_REPORT = "com.github.jk1:gradle-license-report:1.16"
    const val GRADLE_DETEKT = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.15.0"
}

object Deps {
    // Android (https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/buildSrc/src/main/kotlin/androidx/build/dependencies/Dependencies.kt)
    const val ANDROID_DESUGAR_JDK_LIBS = "com.android.tools:desugar_jdk_libs:1.1.1" // https://github.com/google/desugar_jdk_libs/blob/master/CHANGELOG.md
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.2.0"
    const val ANDROIDX_RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.0-beta01"
    const val ANDROIDX_PREFERENCE = "androidx.preference:preference-ktx:1.1.1"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.4"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.3.2"
    const val ANDROIDX_ACTIVITY_KTX = "androidx.activity:activity-ktx:1.2.0-rc01"
    const val ANDROIDX_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.3.0-rc01"
    const val ANDROIDX_STARTUP = "androidx.startup:startup-runtime:1.0.0"
    const val ANDROID_DATASTORE_PREFS = "androidx.datastore:datastore-preferences:1.0.0-alpha05"

    private const val LIFECYCLE_VERSION = "2.3.0-rc01"
    const val ARCH_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
    const val ARCH_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val ARCH_LIFECYCLE_SAVE_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$LIFECYCLE_VERSION"
    const val LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:$LIFECYCLE_VERSION"
    const val LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"

    const val ARCH_NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$ANDROIDX_NAVIGATION_VERSION"
    const val ARCH_NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$ANDROIDX_NAVIGATION_VERSION"

    private const val WORKMANAGER_VERSION = "2.5.0-beta02"
    const val ARCH_WORK_RUNTIME = "androidx.work:work-runtime-ktx:$WORKMANAGER_VERSION"
    const val ARCH_WORK_GCM = "androidx.work:work-gcm:$WORKMANAGER_VERSION" // Remove with min SDK 23
    const val WORKMANAGER_TOOLS = "org.dbtools:workmanager-tools:1.13.0"

    const val ANDROID_MATERIAL = "com.google.android.material:material:1.3.0-beta01" // https://github.com/material-components/material-components-android/releases

    // Play Services
    const val PLAYSERVICE_CORE = "com.google.android.play:core-ktx:1.8.1" // https://developer.android.com/reference/com/google/android/play/core/release-notes
    const val PLAYSERVICE_KOTLINX_COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$COROUTINES_VERSION"
    // const val PLAYSERVICE_LICENSES = "com.google.android.gms:play-services-oss-licenses:17.0.0"

    // Firebase - https://firebase.google.com/support/release-notes/android
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:26.2.0" // automatically sets the version for all firebase libs below
    const val FIREBASE_CORE = "com.google.firebase:firebase-core"
    const val FIREBASE_PERF = "com.google.firebase:firebase-perf"
    const val FIREBASE_CONFIG = "com.google.firebase:firebase-config-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics-ktx"

    // Code
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"

    // Inject
    private const val ANDROIDX_HILT_VERSION = "1.0.0-alpha02"
    const val HILT = "com.google.dagger:hilt-android:$DAGGER_HILT_VERSION"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:$DAGGER_HILT_VERSION"
    const val HILT_TESTING = "com.google.dagger:hilt-android-test:$DAGGER_HILT_VERSION" // integration tests ONLY (does not support JVM tests)
    const val ANDROIDX_HILT_WORK = "androidx.hilt:hilt-work:$ANDROIDX_HILT_VERSION"
    const val ANDROIDX_HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:$ANDROIDX_HILT_VERSION"
    const val ANDROIDX_HILT_COMPILER = "androidx.hilt:hilt-compiler:$ANDROIDX_HILT_VERSION"

    // Standard dagger is needed for unit tests ONLY
    private const val DAGGER_VERSION = "2.30.1"
    const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"

    // Database
    private const val ROOM_VERSION = "2.3.0-alpha04"
    const val ARCH_ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ARCH_ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    const val ARCH_ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    private const val DBTOOLS_VERSION = "5.8.0"
    const val DBTOOLS_ROOM = "org.dbtools:dbtools-room:$DBTOOLS_VERSION"
    const val DBTOOLS_ROOM_SQLITE = "org.dbtools:dbtools-room-sqliteorg:$DBTOOLS_VERSION"

    // Network
    private const val OKHTTP_VERSION = "4.9.0" // https://github.com/square/okhttp/blob/master/CHANGELOG.md
    const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"

    // Dev
    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:2.6"

    // Test - Integration
    private const val ESPRESSO_VERSION = "3.3.0"
    const val TEST_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val TEST_ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:$ESPRESSO_VERSION"
    const val TEST_RUNNER = "androidx.test:runner:1.3.0"
    const val TEST_RULES = "androidx.test:rules:1.3.0"
    const val TEST_ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.2"

    // Test - JUnit
    private const val JUNIT_VERSION = "5.7.0"
    const val TEST_JUNIT = "org.junit.jupiter:junit-jupiter:$JUNIT_VERSION"
    const val TEST_JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$JUNIT_VERSION"
    const val TEST_OKHTTP_MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:$OKHTTP_VERSION"
    const val TEST_MOCKITO_CORE = "org.mockito:mockito-core:3.7.0"
    const val TEST_XERIAL_SQLITE = "org.xerial:sqlite-jdbc:3.34.0"
    const val TEST_ARCH_ROOM_TESTING = "androidx.room:room-testing:$ROOM_VERSION"
    const val TEST_DBTOOLS_ROOM_JDBC = "org.dbtools:dbtools-room-jdbc:$DBTOOLS_VERSION"
    const val TEST_KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
    const val TEST_MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}