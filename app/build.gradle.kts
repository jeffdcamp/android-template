
import com.github.jk1.license.filter.DependencyFilter
import com.github.jk1.license.filter.ExcludeTransitiveDependenciesFilter
import com.github.jk1.license.filter.LicenseBundleNormalizer
import com.github.jk1.license.render.CsvReportRenderer
import com.github.jk1.license.render.InventoryHtmlReportRenderer
import com.github.jk1.license.render.JsonReportRenderer
import com.github.jk1.license.render.ReportRenderer
import com.github.jk1.license.render.SimpleHtmlReportRenderer
import java.util.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs.kotlin")
    id("io.fabric")
    id("com.github.triplet.play") version "2.6.1"
    id("com.github.jk1.dependency-license-report") version "1.12"
}

// Manifest version information
val buildTime = Date().time
val versionCodeAppName = "android-template"
val minVersionCode = 1001
val appVersionCode = VersionCode.readVersionCode(versionCodeAppName, minVersionCode)
val appVersionName = "1.0.0 ($appVersionCode.${System.getenv("BUILD_NUMBER")})"

kapt {
    javacOptions {
        // Increase the max count of errors from annotation processors. (Default is 100)
        option("-Xmaxerrs", 500)
    }
}

// Kotlin Libraries targeting Java8 bytecode can cause the following error (such as okHttp 4.x):
// "Cannot inline bytecode built with JVM target 1.8 into bytecode that is being built with JVM target 1.6. Please specify proper '-jvm-target' option"
// The following is added to allow the Kotlin Compiler to compile properly
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
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
        isEnabled = true
    }

    lintOptions {
        isAbortOnError = true
        disable("InvalidPackage")
    }

    // defined values my* in ~/.gradle/gradle.properties
    signingConfigs {
        create("upload") {
            val myUploadKeystore: String? by project
            val myUploadKeystorePassword: String by project
            val myUploadKeyAlias: String by project
            val myUploadKeyPassword: String by project

            if (myUploadKeystore != null) {
                storeFile = File(myUploadKeystore)
                storePassword = myUploadKeystorePassword
                keyAlias = myUploadKeyAlias
                keyPassword = myUploadKeyPassword
            }
        }

        create("prod") {
            val myProdKeystore: String? by project
            val myProdKeystorePassword: String by project
            val myProdKeyAlias: String by project
            val myProdKeyPassword: String by project

            if (myProdKeystore != null) {
                storeFile = File(myProdKeystore)
                storePassword = myProdKeystorePassword
                keyAlias = myProdKeyAlias
                keyPassword = myProdKeyPassword
            }
        }
    }

    buildTypes {
        getByName("debug") {
            versionNameSuffix = " DEV"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "0l") // to improve build times, do allow change on every build

            // Enable signing to test Firebase
            // signingConfig = signingConfigs.getByName("upload")
        }
        create("alpha") {
            initWith(getByName("release"))
            versionNameSuffix = " ALPHA"
            applicationIdSuffix = ".alpha"
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            // isDebuggable = true
            signingConfig = signingConfigs.getByName("upload")
        }
        create("beta") {
            initWith(getByName("release"))
            versionNameSuffix = " BETA"
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            signingConfig = signingConfigs.getByName("upload")
        }
        getByName("release") {
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")
            versionNameSuffix = ""
            //minifyEnabled true
            //shrinkResources true
            signingConfig = signingConfigs.getByName("upload")
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
    implementation(Deps.ANDROIDX_APPCOMPAT)
    implementation(Deps.ANDROIDX_RECYCLERVIEW)
    implementation(Deps.ANDROIDX_PREFERENCE)
    implementation(Deps.ANDROID_MATERIAL)
    implementation(Deps.ANDROIDX_ANNOTATIONS)
    implementation(Deps.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Deps.ANDROIDX_CORE)
    implementation(Deps.ANDROIDX_ACTIVITY_KTX)
    implementation(Deps.ANDROIDX_FRAGMENT_KTX)

    // Play Service
    implementation(Deps.PLAYSERVICE_CORE)

    // Firebase
    implementation(Deps.FIREBASE_CORE)
//    implementation(Deps.FIREBASE_PERF)
//    implementation(Deps.FIREBASE_CONFIG)
    implementation(Deps.CRASHLYTICS)

    // Code
    implementation(Deps.KOTLIN_STD_LIB)
    implementation(Deps.KOTLIN_SERIALIZATION)
    implementation(Deps.COROUTINES)
    implementation(Deps.EXTRAS_DELEGATES)
    implementation(Deps.THREETEN_ABP)
    implementation(Deps.TIMBER)

    // Inject
    implementation(Deps.DAGGER)
    kapt(Deps.DAGGER_COMPILER)
    implementation(Deps.VIEWMODEL_INJECT)
    kapt(Deps.VIEWMODEL_INJECT_PROCESSOR)
    implementation(Deps.WORKER_INJECT)
    kapt(Deps.WORKER_INJECT_PROCESSOR)

    // UI
    implementation(Deps.MATERIAL_DIALOGS_CORE)
    implementation(Deps.MATERIAL_DIALOGS_DATETIME)
    implementation(Deps.MATERIAL_DIALOGS_LIFECYCLE)

    // === Android Architecture Components ===
    implementation(Deps.ARCH_LIFECYCLE_EXT)
    implementation(Deps.ARCH_LIFECYCLE_RUNTIME)
    implementation(Deps.ARCH_LIFECYCLE_VIEWMODEL)
    implementation(Deps.ARCH_LIFECYCLE_SAVE_STATE)
    implementation(Deps.LIVE_DATA_KTX)

    // Navigation
    implementation(Deps.ARCH_NAVIGATION_FRAGMENT)
    implementation(Deps.ARCH_NAVIGATION_UI)

    // WorkManager
    implementation(Deps.ARCH_WORK_RUNTIME)
    implementation(Deps.ARCH_WORK_GCM)

    // Database
    implementation(Deps.ARCH_ROOM_RUNTIME)
    implementation(Deps.ARCH_ROOM_KTX)
    kapt(Deps.ARCH_ROOM_COMPILER)
    implementation(Deps.DBTOOLS_ROOM)

    // Custom SQLite database
    // (for use of SqliteOrgSQLiteOpenHelperFactory in AppModule.kt)
    //implementation(Deps.DBTOOLS_ROOM_SQLITE)

    // Debug Database (emulator: adb forward tcp:8080 tcp:8080) (https://github.com/amitshekhariitbhu/Android-Debug-Database)
    // debugimplementation("com.amitshekhar.android:debug-db:1.0.4")

    // Network
    implementation(Deps.OKHTTP)
    implementation(Deps.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Deps.RETROFIT)
    implementation(Deps.KOTLIN_RETROFIT_CONVERTER)

    // Test (Integration)
    androidTestImplementation(Deps.TEST_ESPRESSO_CORE)
    androidTestImplementation(Deps.TEST_ESPRESSO_CONTRIB)
    androidTestImplementation(Deps.TEST_RUNNER)
    androidTestImplementation(Deps.TEST_RULES)
    androidTestImplementation(Deps.TEST_ANDROIDX_JUNIT)

    // Test (Unit)
    testImplementation(Deps.TEST_JUNIT)
    testRuntimeOnly(Deps.TEST_JUNIT_ENGINE)
    testImplementation(Deps.TEST_MOCKITO_KOTLIN)
    testImplementation(Deps.TEST_MOCKITO_CORE)
    testImplementation(Deps.TEST_KOTLIN_COROUTINES_TEST)
    testImplementation(Deps.TEST_THREETENBP)
    testImplementation(Deps.TEST_OKHTTP_MOCKWEBSERVER)
    testImplementation(Deps.TEST_XERIAL_SQLITE)
    testImplementation(Deps.TEST_ARCH_ROOM_TESTING)
    testImplementation(Deps.TEST_DBTOOLS_ROOM_JDBC)
    kaptTest(Deps.DAGGER_COMPILER)
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

play {
    val myServiceAccountCreds: String? by project
    serviceAccountCredentials = File(myServiceAccountCreds ?: "api-playstore-dummy.json")
    track = "internal"
    defaultToAppBundles = true
}

// ./gradlew generateLicenseReport
licenseReport {
    // only include run-time dependencies
    configurations = arrayOf("releaseRuntimeClasspath")

    // Renderers
    renderers = arrayOf<ReportRenderer>(
            JsonReportRenderer("../../../src/main/assets/licenses.json"), // required for acknowledgements screen in app
            SimpleHtmlReportRenderer("licenses-simple.html"),
            InventoryHtmlReportRenderer("licenses-groups.html", versionCodeAppName), // identify unique licenses
            CsvReportRenderer("licenses.csv") // preferred by legal (spreadsheet form)
    )

    // Filters
    filters = arrayOf<DependencyFilter>(
            LicenseBundleNormalizer(), //  group common known licenses
            ExcludeTransitiveDependenciesFilter() // only include top level dependencies
    )

    // excludes
    excludeGroups = arrayOf(
            // Internal created libraries
            "org.jdc",

            // Commercial Licenses
            "com.xxx.xxx"
    )
}

// this must be at the bottom of the file
apply(plugin = "com.google.gms.google-services")