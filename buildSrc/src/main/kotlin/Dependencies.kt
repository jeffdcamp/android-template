object Libs {
    // ===== Kotlin =====
    object Kotlin {
        private const val VERSION = "1.4.32"
        const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$VERSION"

        object Serialization {
            const val GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-serialization:$VERSION"
            const val JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0"
        }

        object Coroutines {
            private const val VERSION = "1.4.3"

            const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VERSION"
            const val PLAY_SERVICES = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$VERSION"
            const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
        }
    }

    // ===== Google/Android/AndroidX =====
    object Android {
        const val GRADLE_PLUGIN = "com.android.tools.build:gradle:7.0.0-alpha14"
        const val DESUGAR = "com.android.tools:desugar_jdk_libs:1.1.5"
    }

    object AndroidX {
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.3.0-rc01"
        const val PREFERENCE_KTX = "androidx.preference:preference-ktx:1.1.1"
        const val CORE_KTX = "androidx.core:core-ktx:1.5.0-rc01"
        const val ACTIVITY_KTX = "androidx.activity:activity-ktx:1.2.2"
        const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:1.3.2"
        const val STARTUP = "androidx.startup:startup-runtime:1.0.0"
        const val DATASTORE_PREFS = "androidx.datastore:datastore-preferences:1.0.0-alpha08"

        object Layout {
            const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:1.2.0"
            const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.4"
        }

        object Room {
            private const val VERSION = "2.3.0-rc01"
            const val RUNTIME = "androidx.room:room-runtime:$VERSION"
            const val KTX = "androidx.room:room-ktx:$VERSION"
            const val COMPILER = "androidx.room:room-compiler:$VERSION"
        }

        object Hilt {
            private const val VERSION = "1.0.0-beta01"
            const val WORK = "androidx.hilt:hilt-work:$VERSION"
            const val COMPILER = "androidx.hilt:hilt-compiler:$VERSION" // required for workers (NoSuchMethodException will throw during runtime when running a worker) (https://developer.android.com/training/dependency-injection/hilt-jetpack#workmanager)
        }

        object Lifecycle {
            private const val VERSION = "2.4.0-alpha01"
            const val RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:$VERSION"
            const val VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
            const val VIEWMODEL_SAVESTATE = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$VERSION"
            const val COMMON_JAVA8 = "androidx.lifecycle:lifecycle-common-java8:$VERSION"
        }

        object Navigation {
            private const val VERSION = "2.3.5"
            const val SAFE_ARGS_GRADLE_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$VERSION"
            const val FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:$VERSION"
            const val UI_KTX = "androidx.navigation:navigation-ui-ktx:$VERSION"
        }

        object WorkManager {
            private const val VERSION = "2.5.0"
            const val RUNTIME = "androidx.work:work-runtime-ktx:$VERSION"
            const val GCM = "androidx.work:work-gcm:$VERSION" // Remove with min SDK 23+
        }

        object Test {
            const val RUNNER = "androidx.test:runner:1.3.0"
            const val RULES = "androidx.test:rules:1.3.0"
            const val JUNIT_EXT = "androidx.test.ext:junit:1.1.2"

            object Espresso {
                private const val VERSION = "3.3.0"
                const val CORE = "androidx.test.espresso:espresso-core:$VERSION"
                const val CONTRIB = "androidx.test.espresso:espresso-contrib:$VERSION"
            }
        }
    }

    object Google {
        const val KSP_GRADLE_PLUGIN = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.4.30-1.0.0-alpha05" // https://maven.google.com/web/index.html#com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin
        const val SERVICES_GRADLE_PLUGIN = "com.google.gms:google-services:4.3.5"
        const val MATERIAL = "com.google.android.material:material:1.3.0" // https://github.com/material-components/material-components-android/releases
        const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:2.33" // Standard dagger is needed for unit tests ONLY

        object Firebase {
            const val CRASHLYTICS_GRADLE_PLUGIN = "com.google.firebase:firebase-crashlytics-gradle:2.5.2"
            const val APP_DISTRIBUTION_GRADLE_PLUGIN = "com.google.firebase:firebase-appdistribution-gradle:2.1.0"
            const val PERF_GRADLE_PLUGIN = "com.google.firebase:perf-plugin:1.3.4"

            // Firebase - https://firebase.google.com/support/release-notes/android
            const val BOM = "com.google.firebase:firebase-bom:26.8.0" // automatically sets the version for all firebase libs below
            const val CORE = "com.google.firebase:firebase-core"
            const val PERF = "com.google.firebase:firebase-perf"
            const val CONFIG = "com.google.firebase:firebase-config-ktx"
            const val CRASHLYTICS = "com.google.firebase:firebase-crashlytics-ktx"
            const val ANALYTICS = "com.google.firebase:firebase-analytics-ktx"
        }

        object Hilt {
            private const val VERSION = "2.33-beta"
            const val GRADLE_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$VERSION"
            const val ANDROID = "com.google.dagger:hilt-android:$VERSION"
            const val COMPILER = "com.google.dagger:hilt-compiler:$VERSION"
            const val TESTING = "com.google.dagger:hilt-android-testing:$VERSION"
            const val TEST_COMPILER = "com.google.dagger:dagger-compiler:$VERSION"
        }

        object Play {
            const val CORE = "com.google.android.play:core-ktx:1.8.1" // https://developer.android.com/reference/com/google/android/play/core/release-notes
            // const val LICENSES = "com.google.android.gms:play-services-oss-licenses:17.0.0"
        }
    }

    // ===== 3rd Party =====
    const val VERSIONS_GRADLE_PLUGIN = "com.github.ben-manes:gradle-versions-plugin:0.38.0"
    const val PLAY_PUBLISHER_GRADLE_PLUGIN = "com.github.triplet.gradle:play-publisher:3.3.0-agp4.2"
    const val LICENSE_REPORT_GRADLE_PLUGIN = "com.github.jk1:gradle-license-report:1.16"
    const val DETEKT_GRADLE_PLUGIN = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.16.0"

    const val WORKMANAGER_TOOLS = "org.dbtools:workmanager-tools:1.14.0"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val TIMBER = "com.jakewharton.timber:timber:4.7.1"
    const val LEAK_CANARY = "com.squareup.leakcanary:leakcanary-android:2.7"

    object DBTools {
        private const val VERSION = "6.0.0-rc01"
        const val ROOM = "org.dbtools:dbtools-room:$VERSION"
        const val ROOM_SQLITE = "org.dbtools:dbtools-room-sqliteorg:$VERSION"
        const val ROOM_JDBC_TEST = "org.dbtools:dbtools-room-jdbc-test:$VERSION"
    }

    object OkHttp {
        const val BOM = "com.squareup.okhttp3:okhttp-bom:4.9.1"
        const val OKHTTP = "com.squareup.okhttp3:okhttp"
        const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor"
        const val MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver"
    }

    object Mockito {
        const val CORE = "org.mockito:mockito-core:3.9.0"
        const val KOTLIN = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    }

    object JUnit {
        const val BOM = "org.junit:junit-bom:5.7.1"
        const val JUPITER = "org.junit.jupiter:junit-jupiter"
        const val ENGINE = "org.junit.jupiter:junit-jupiter-engine"
    }

    object Test {
        const val XERIAL_SQLITE = "org.xerial:sqlite-jdbc:3.34.0"
    }
}