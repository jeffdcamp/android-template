
import java.util.Date

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

apply(plugin = "androidx.navigation.safeargs.kotlin")

// Manifest version information
val buildTime = Date().time
val versionCodeAppName = "android-template"
val minVersionCode = 1000
val appVersionCode = VersionCode.readVersionCode(versionCodeAppName, minVersionCode)
val appVersionName = "1.0.0 ($appVersionCode.${System.getProperty("BUILD_NUMBER")})"

kapt {
    javacOptions {
        // Increase the max count of errors from annotation processors. (Default is 100)
        option("-Xmaxerrs", 500)
    }
}

android {
    compileSdkVersion(AndroidSdk.COMPILE)

    defaultConfig {
        minSdkVersion(AndroidSdk.MIN)
        targetSdkVersion(AndroidSdk.TARGET)

        versionCode = appVersionCode
        versionName = appVersionName

        buildConfigField("String", "BUILD_NUMBER", "\"${System.getProperty("BUILD_NUMBER")}\"")
        buildConfigField("String", "USER_AGENT_APP_NAME", "\"AndroidTemplate\"")
        buildConfigField("String", "ANALYTICS_KEY", "\"${getAnalyticsKey()}\"")

        multiDexEnabled = true

        // used by Room, to test migrations
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schema")
            }
        }

        // Espresso
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    dataBinding {
        setEnabled(true)
    }

    lintOptions {
        isAbortOnError = true
        disable("InvalidPackage")
    }

    // defined values my* in ~/.gradle/gradle.properties
    signingConfigs {
        // restore "by project" to read from Global Gradle file (this is a workaround to allow compile to work on fresh clone)
        val myKeystore = "" // : String by project
        val myKeystorePassword = "" // : String by project
        val myKeyAlias = "" // : String by project
        val myKeyPassword = "" // : String by project

        create("prod") {
            storeFile = File(myKeystore)
            storePassword = myKeystorePassword
            keyAlias = myKeyAlias
            keyPassword = myKeyPassword
        }
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = " DEV"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "0l") // to improve build times, do allow change on every build
        }
        create("alpha") {
            initWith(getByName("release"))
            versionNameSuffix = " ALPHA"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            isDebuggable = true
            signingConfig = signingConfigs.getByName("prod")
        }
        create("beta") {
            initWith(getByName("release"))
            versionNameSuffix = " BETA"
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            signingConfig = signingConfigs.getByName("prod")
        }
        getByName("release") {
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            versionNameSuffix = ""
            //minifyEnabled true
            //shrinkResources true
            signingConfig = signingConfigs.getByName("prod")
        }
    }

    sourceSets {
        getByName("main") {
            java.srcDir("src/main/kotlin")
        }
        getByName("test") {
            java.srcDir("src/test/kotlin")
        }
        getByName("androidTest") {
            assets.srcDir("$projectDir/schemas")
        }
    }
}

dependencies {
    // Android
    implementation(Libs.ANDROIDX_APPCOMPAT)
    implementation(Libs.ANDROIDX_RECYCLERVIEW)
    implementation(Libs.ANDROIDX_PREFERENCE)
    implementation(Libs.ANDROID_MATERIAL)
    implementation(Libs.ANDROIDX_ANNOTATIONS)
    implementation(Libs.ANDROID_MULTIDEX)
    implementation(Libs.ANDROID_MULTIDEX_INSTRUMENTATION)
    implementation(Libs.PLAYSERVICE_ANALYTICS)
//    implementation(Libs.PLAYSERVICE_LICENSES)
    implementation(Libs.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Libs.ANDROIDX_CORE)

    // Code
    implementation(Libs.KOTLIN_STD_LIB)
    implementation(Libs.COROUTINES)
    implementation(Libs.EXTRAS_DELEGATES)
    implementation(Libs.THREETEN_ABP)
    implementation(Libs.TIMBER)
    implementation(Libs.VIEWMODEL_INJECT)
    kapt(Libs.VIEWMODEL_INJECT_PROCESSOR)

    // UI
    implementation(Libs.MATERIAL_DIALOGS_CORE)
    implementation(Libs.MATERIAL_DIALOGS_DATETIME)
    implementation(Libs.MATERIAL_DIALOGS_LIFECYCLE)

    // === Android Architecture Components ===
    implementation(Libs.ARCH_LIFECYCLE_EXT)
    implementation(Libs.ARCH_LIFECYCLE_RUNTIME)
    implementation(Libs.ARCH_LIFECYCLE_VIEWMODEL)
    implementation(Libs.ARCH_LIFECYCLE_COMMON)

    // Navigation
    implementation(Libs.ARCH_NAVIGATION_FRAGMENT)
    implementation(Libs.ARCH_NAVIGATION_UI)

    // WorkManager
    implementation(Libs.ARCH_WORK_RUNTIME)

    // Database
    implementation(Libs.ARCH_ROOM_RUNTIME)
    implementation(Libs.ARCH_ROOM_KTX)
    kapt(Libs.ARCH_ROOM_COMPILER)
    implementation(Libs.DBTOOLS_ROOM)

    // Custom SQLite database
    // (for use of SqliteOrgSQLiteOpenHelperFactory in AppModule.kt)
    //implementation(Libs.DBTOOLS_ROOM_SQLITE)

    // Debug Database (emulator: adb forward tcp:8080 tcp:8080) (https://github.com/amitshekhariitbhu/Android-Debug-Database)
    // debugimplementation("com.amitshekhar.android:debug-db:1.0.4")

    // Network
    implementation(Libs.OKHTTP)
    implementation(Libs.OKHTTP_LOGGING_INTERCEPTOR)

    // JSON Parsing
    implementation(Libs.GSON)
    implementation(Libs.GSON_RETROFIT_CONVERTER)

    // Dagger 2
    implementation(Libs.DAGGER)
    kapt(Libs.DAGGER_COMPILER)

    // Test (Integration)
    androidTestImplementation(Libs.TEST_ESPRESSO_CORE)
    androidTestImplementation(Libs.TEST_ESPRESSO_CONTRIB)
    androidTestImplementation(Libs.TEST_RUNNER)
    androidTestImplementation(Libs.TEST_RULES)
    androidTestImplementation(Libs.TEST_ANDROIDX_JUNIT)

    // Test (Unit)
    testImplementation(Libs.TEST_JUNIT)
    testRuntimeOnly(Libs.TEST_JUNIT_ENGINE)
    testImplementation(Libs.TEST_OKHTTP_MOCKWEBSERVER)
    testImplementation(Libs.TEST_MOCKITO_CORE)
    testImplementation(Libs.TEST_THREETENBP)
    testImplementation(Libs.TEST_XERIAL_SQLITE)
    testImplementation(Libs.TEST_ARCH_ROOM_TESTING)
    testImplementation(Libs.TEST_DBTOOLS_ROOM_JDBC)
    testImplementation(Libs.TEST_KOTLIN_COROUTINES_TEST)
    testImplementation(Libs.TEST_MOCKITO_KOTLIN)
    kaptTest(Libs.DAGGER_COMPILER)
}

// ===== TEST TASKS =====

// create JUnit reports
tasks.withType<Test> {
    useJUnitPlatform()
}

// Rename and place "myAnalyticsKey" in global gradle.properties
fun getAnalyticsKey(): String {
    val myAnalyticsKey: String? by project
    return myAnalyticsKey ?: ""
}

tasks.register("incrementVersionCode") {
    doLast {
        VersionCode.incrementVersionCode(versionCodeAppName, minVersionCode)
    }
}
