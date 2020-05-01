// Versions for Dependencies AND Build Plugins
const val KOTLIN_VERSION = "1.3.72"
const val ANDROIDX_NAVIGATION_VERSION = "2.2.2"

object Deps {
    // Android (https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-dev/buildSrc/src/main/kotlin/androidx/build/dependencies/Dependencies.kt)
    const val ANDROID_DESUGAR_JDK_LIBS = "com.android.tools:desugar_jdk_libs:1.0.5"
    const val ANDROIDX_ANNOTATIONS = "androidx.annotation:annotation:1.1.0"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.1.0"
    const val ANDROIDX_RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.1.0"
    const val ANDROIDX_PREFERENCE = "androidx.preference:preference-ktx:1.1.1"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.2.0"
    const val ANDROIDX_ACTIVITY_KTX = "androidx.activity:activity-ktx:1.1.0"
    const val ANDROIDX_FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.2.4"

    private const val LIFECYCLE_VERSION = "2.2.0"
    const val ARCH_LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
    const val ARCH_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:$LIFECYCLE_VERSION"
    const val ARCH_LIFECYCLE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val ARCH_LIFECYCLE_SAVE_STATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$LIFECYCLE_VERSION"
    const val LIVE_DATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"

    const val ARCH_NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$ANDROIDX_NAVIGATION_VERSION"
    const val ARCH_NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$ANDROIDX_NAVIGATION_VERSION"

    private const val WORKMANAGER_VERSION = "2.3.4"
    const val ARCH_WORK_RUNTIME = "androidx.work:work-runtime-ktx:$WORKMANAGER_VERSION"
    const val ARCH_WORK_GCM = "androidx.work:work-gcm:$WORKMANAGER_VERSION" // Remove with min SDK 23

    const val ANDROID_MATERIAL = "com.google.android.material:material:1.1.0" // https://github.com/material-components/material-components-android/releases

    // Play Services
    const val PLAYSERVICE_CORE = "com.google.android.play:core-ktx:1.7.0" // https://developer.android.com/reference/com/google/android/play/core/release-notes
    // const val PLAYSERVICE_LICENSES = "com.google.android.gms:play-services-oss-licenses:17.0.0"

    // Firebase - https://firebase.google.com/support/release-notes/android
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:25.3.0" // automatically sets the version for all firebase libs below
    const val FIREBASE_CORE = "com.google.firebase:firebase-core"
    const val FIREBASE_PERF = "com.google.firebase:firebase-perf"
    const val FIREBASE_CONFIG = "com.google.firebase:firebase-config-ktx"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics"

    // Code
    const val KOTLIN_STD_LIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$KOTLIN_VERSION"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0"
    private const val COROUTINES_VERSION = "1.3.4"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.8.1"
    const val KOTLIN_RETROFIT_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.5.0"
    const val EXTRAS_DELEGATES = "me.eugeniomarletti:android-extras-delegates:1.0.5"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"

    // Inject
    private const val DAGGER_VERSION = "2.27"
    const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"

    // Inject helpers
    private const val FRAGMENT_INJECT_VERSION = "1.0.0"
    const val FRAGMENT_INJECT = "com.vikingsen.inject:fragment-inject:$FRAGMENT_INJECT_VERSION"
    const val FRAGMENT_INJECT_PROCESSOR = "com.vikingsen.inject:fragment-inject-processor:$FRAGMENT_INJECT_VERSION"
    private const val VIEWMODEL_INJECT_VERSION = "0.3.3"
    const val VIEWMODEL_INJECT = "com.vikingsen.inject:viewmodel-inject:$VIEWMODEL_INJECT_VERSION"
    const val VIEWMODEL_INJECT_PROCESSOR = "com.vikingsen.inject:viewmodel-inject-processor:$VIEWMODEL_INJECT_VERSION"
    private const val WORKER_INJECT_VERSION = "0.2.2"
    const val WORKER_INJECT = "com.vikingsen.inject:worker-inject:$WORKER_INJECT_VERSION"
    const val WORKER_INJECT_PROCESSOR = "com.vikingsen.inject:worker-inject-processor:$WORKER_INJECT_VERSION"

    // Database
    private const val ROOM_VERSION = "2.2.5"
    const val ARCH_ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ARCH_ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    const val ARCH_ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    private const val DBTOOLS_VERSION = "5.6.0"
    const val DBTOOLS_ROOM = "org.dbtools:dbtools-room:$DBTOOLS_VERSION"
    const val DBTOOLS_ROOM_SQLITE = "org.dbtools:dbtools-room-sqliteorg:$DBTOOLS_VERSION"

    // Network
    private const val OKHTTP_VERSION = "4.6.0" // https://github.com/square/okhttp/blob/master/CHANGELOG.md
    const val OKHTTP = "com.squareup.okhttp3:okhttp:$OKHTTP_VERSION"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$OKHTTP_VERSION"

    // Test - Integration
    private const val ESPRESSO_VERSION = "3.2.0"
    const val TEST_ESPRESSO_CORE = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val TEST_ESPRESSO_CONTRIB = "androidx.test.espresso:espresso-contrib:$ESPRESSO_VERSION"
    const val TEST_RUNNER = "androidx.test:runner:1.2.0"
    const val TEST_RULES = "androidx.test:rules:1.2.0"
    const val TEST_ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.1"

    // Test - JUnit
    private const val JUNIT_VERSION = "5.6.2"
    const val TEST_JUNIT = "org.junit.jupiter:junit-jupiter:$JUNIT_VERSION"
    const val TEST_JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$JUNIT_VERSION"
    const val TEST_OKHTTP_MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:$OKHTTP_VERSION"
    const val TEST_MOCKITO_CORE = "org.mockito:mockito-core:3.3.3"
    const val TEST_XERIAL_SQLITE = "org.xerial:sqlite-jdbc:3.30.1"
    const val TEST_ARCH_ROOM_TESTING = "androidx.room:room-testing:$ROOM_VERSION"
    const val TEST_DBTOOLS_ROOM_JDBC = "org.dbtools:dbtools-room-jdbc:$DBTOOLS_VERSION"
    const val TEST_KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
    const val TEST_MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
}