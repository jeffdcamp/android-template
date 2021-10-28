import java.util.Date

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.firebase.crashlytics")
    id("org.dbtools.license-manager")
    id("de.undercouch.download")
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.playPublisher)
    alias(libs.plugins.kotlin.serialization)
}

kapt {
    javacOptions {
        // Increase the max count of errors from annotation processors. (Default is 100)
        option("-Xmaxerrs", 500)
    }

    correctErrorTypes = true // prevent NonExistentClass in errors (https://kotlinlang.org/docs/kapt.html#non-existent-type-correction)
}

android {
    compileSdk = AppInfo.AndroidSdk.COMPILE

    defaultConfig {
        minSdk = AppInfo.AndroidSdk.MIN
        targetSdk = AppInfo.AndroidSdk.TARGET

        applicationId = AppInfo.APPLICATION_ID
        versionCode = AppInfo.Version.CODE
        versionName = AppInfo.Version.NAME

        buildConfigField("String", "BUILD_NUMBER", "\"${System.getProperty("BUILD_NUMBER")}\"")
        buildConfigField("String", "USER_AGENT_APP_NAME", "\"AndroidTemplate\"")
        buildConfigField("String", "ANALYTICS_KEY", "\"${getAnalyticsKey()}\"")

        // used by Room, to test migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schema")
            arg("room.incremental", "true")
        }

        // Espresso
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
        languageVersion = "1.4"
        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.ExperimentalStdlibApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-Xopt-in=kotlinx.coroutines.FlowPreview",
            "-Xopt-in=kotlin.time.ExperimentalTime",
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",

            // use the following to ignore enforcement version of Kotlin with Compose
            //"-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    lint {
        ignoreTestSources = true
        abortOnError = true
        disable.addAll(listOf("InvalidPackage", "DialogFragmentCallbacksDetector"))
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
        debug {
            versionNameSuffix = " DEV"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "0l") // to improve build times, do allow change on every build

            // Enable signing to test Firebase
            // signingConfig = signingConfigs.getByName("upload")
        }
        create("alpha") {
            // todo remove initWith(...)?
            // * 2021-04-30 no longer exists with AGP 7.0.0-alpha15
            // * Code for in AGP 7.0.0-alpha14 AbstractBuildType.initWith() only copies values from other variant... which is covered below (see https://android.googlesource.com/platform/tools/base/+/mirror-goog-studio-master-dev/build-system/gradle-core/src/main/java/com/android/builder/core/AbstractBuildType.kt)
            // initWith(getByName("release"))

            versionNameSuffix = " ALPHA"
            applicationIdSuffix = ".alpha"
            buildConfigField("long", "BUILD_TIME", "${Date().time}l")
            // isDebuggable = true
            signingConfig = signingConfigs.getByName("upload")
        }
        create("beta") {
            // todo remove initWith(...)?
            // * 2021-04-30 no longer exists with AGP 7.0.0-alpha15
            // * Code for in AGP 7.0.0-alpha14 AbstractBuildType.initWith() only copies values from other variant... which is covered below (see https://android.googlesource.com/platform/tools/base/+/mirror-goog-studio-master-dev/build-system/gradle-core/src/main/java/com/android/builder/core/AbstractBuildType.kt)
            // initWith(getByName("release"))

            versionNameSuffix = " BETA"
            buildConfigField("long", "BUILD_TIME", "${Date().time}l")
            signingConfig = signingConfigs.getByName("upload")
        }
        release {
            versionNameSuffix = ""
            buildConfigField("long", "BUILD_TIME", "${Date().time}l")
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
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.androidx.appcompat) // AppCompatActivity
    implementation(libs.androidx.preference)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.datastorePrefs)

    // Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.util)
    implementation(libs.compose.material.material)
//    implementation(libs.compose.material.material3)
//    implementation(libs.compose.material.iconsext)
//    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
//    implementation(libs.compose.constraintlayout)
    implementation(libs.compose.activity)
//    implementation(libs.compose.theme.adapter) // enables reuse of Material themes defined in XML for theming in Jetpack Compose

    // Play Service
    implementation(libs.google.play.core)
    implementation(libs.kotlin.coroutines.playServices)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.core)
    //implementation(libs.google.firebase.perf)
    implementation(libs.google.firebase.config)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    // Code
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.timber)

    // Inject
    kapt(libs.google.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.google.hilt.library)
    implementation(libs.androidx.hilt.work)

    // Android Architecture Components
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.common.java8)

    // Navigation
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.gcm)
    implementation(libs.workmanagertools)

    // Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.dbtools.room)

    // Custom SQLite database
    // (for use of SqliteOrgSQLiteOpenHelperFactory)
    // implementation(libs.dbtools.room)
    //implementation(libs.dbtools.roomSqlite)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.retrofit)

    // Dev
    debugImplementation(libs.leakCanary)

    // Test (Integration)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.junit)

    // Test (Unit)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.mockK)
    testImplementation(libs.truth)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.okhttp.mockwebserver)
    testImplementation(libs.xerial.sqlite)
    testImplementation(libs.dbtools.roomJdbcTest)

    // use regular dagger for unit tests
    // (2020-06-11: "Currently, Hilt only supports Android instrumentation and Robolectric tests. Hilt cannot be used in vanilla JVM tests,
    // but it does not prevent you from writing these tests as you would normally." (https://dagger.dev/hilt/testing)
    kaptTest(libs.dagger.compiler)
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
        VersionCode.incrementVersionCode(AppInfo.Version.APP_NAME, AppInfo.Version.MIN)
    }
}

// download detekt config file
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadDetektConfig") {
    download {
        onlyIf { !file("build/config/detektConfig.yml").exists() }
        src("https://raw.githubusercontent.com/ICSEng/AndroidPublic/main/detektConfig.yml")
        dest("build/config/detektConfig.yml")
    }
}

tasks {
    // make sure when running detekt, the config file is downloaded
    withType<io.gitlab.arturbosch.detekt.Detekt> {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "1.8"
        dependsOn("downloadDetektConfig")
    }
}

// ./gradlew detekt
detekt {
    allRules = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config = files("$projectDir/build/config/detektConfig.yml") // point to your custom config defining rules to run, overwriting default behavior
//    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    reports {
        html.enabled = true // observe findings in your browser with structure and code snippets
        xml.enabled = true // checkstyle like format mainly for integrations like Jenkins
        txt.enabled = true // similar to the console output, contains issue signature to manually edit baseline files
    }
}

// TripleT / Google Play Publisher
play {
    val myServiceAccountCreds: String? by project
    serviceAccountCredentials.set(File(myServiceAccountCreds ?: "api-playstore-dummy.json"))
    track.set("internal")
    defaultToAppBundles.set(true)
}

// ./gradlew createLicenseReports
// ./gradlew --stacktrace -i createLicenseReports
licenseManager {
    variantName = "release"
    outputDirs = listOf("./src/main/assets", "./build/licenses")
    excludeGroups = listOf("org.dbtools")
    createHtmlReport = true
}

// this must be at the bottom of the file
apply(plugin = "com.google.gms.google-services")