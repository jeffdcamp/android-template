// Versions for Dependencies AND Build Plugins
const val KOTLIN_VERSION = "1.3.31"
const val ANDROIDX_NAVIGATION_VERSION = "2.0.0"
const val PLAYSERVICE_LICENSE_VERSION = "0.9.1"

object Deps {
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
    private const val COROUTINES_VERSION = "1.2.1"
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$COROUTINES_VERSION"
    private const val DAGGER_VERSION = "2.23.1"
    const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"
    const val KOTLIN_RETROFIT_CONVERTER = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.4.0"
    const val EXTRAS_DELEGATES = "me.eugeniomarletti:android-extras-delegates:1.0.5"
    const val THREETEN_ABP = "com.jakewharton.threetenabp:threetenabp:1.2.1"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
    const val VIEWMODEL_INJECT = "com.vikingsen.inject:viewmodel-inject:0.1.1"
    const val VIEWMODEL_INJECT_PROCESSOR = "com.vikingsen.inject:viewmodel-inject-processor:0.1.1"

    // UI
    private const val MATERIAL_DIALOGS_VERSION = "2.8.1"
    const val MATERIAL_DIALOGS_CORE = "com.afollestad.material-dialogs:core:$MATERIAL_DIALOGS_VERSION"
    const val MATERIAL_DIALOGS_DATETIME = "com.afollestad.material-dialogs:datetime:$MATERIAL_DIALOGS_VERSION"
    const val MATERIAL_DIALOGS_LIFECYCLE = "com.afollestad.material-dialogs:lifecycle:$MATERIAL_DIALOGS_VERSION"
    const val MATERIAL_DIALOGS_INPUT = "com.afollestad.material-dialogs:input:$MATERIAL_DIALOGS_VERSION"

    // Database
    private const val ROOM_VERSION = "2.1.0-rc01"
    const val ARCH_ROOM_RUNTIME = "androidx.room:room-runtime:$ROOM_VERSION"
    const val ARCH_ROOM_KTX = "androidx.room:room-ktx:$ROOM_VERSION"
    const val ARCH_ROOM_COMPILER = "androidx.room:room-compiler:$ROOM_VERSION"
    private const val DBTOOLS_VERSION = "4.9.1"
    const val DBTOOLS_ROOM = "org.dbtools:dbtools-room:$DBTOOLS_VERSION"
    const val DBTOOLS_ROOM_SQLITE = "org.dbtools:dbtools-room-sqliteorg:$DBTOOLS_VERSION"

    // Network
    private const val OKHTTP_VERSION = "3.14.2"
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
    private const val JUNIT_VERSION = "5.4.1"
    const val TEST_JUNIT = "org.junit.jupiter:junit-jupiter:$JUNIT_VERSION"
    const val TEST_JUNIT_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$JUNIT_VERSION"
    const val TEST_OKHTTP_MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:$OKHTTP_VERSION"
    const val TEST_MOCKITO_CORE = "org.mockito:mockito-core:2.28.2"
    const val TEST_THREETENBP = "org.threeten:threetenbp:1.4.0"
    const val TEST_XERIAL_SQLITE = "org.xerial:sqlite-jdbc:3.27.2.1"
    const val TEST_ARCH_ROOM_TESTING = "androidx.room:room-testing:$ROOM_VERSION"
    const val TEST_DBTOOLS_ROOM_JDBC = "org.dbtools:dbtools-room-jdbc:$DBTOOLS_VERSION"
    const val TEST_KOTLIN_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$COROUTINES_VERSION"
    const val TEST_MOCKITO_KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
}