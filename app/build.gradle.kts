@file:Suppress("UnstableApiUsage")

import java.util.Date

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.appdistribution")
    id("org.dbtools.license-manager")
    id("de.undercouch.download") version "5.5.0"
    id("com.spotify.ruler")
    id("org.gradle.jacoco")
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    id("com.github.triplet.play") // alias(libs.plugins.playPublisher) (3.7.0 still conflicts with appdistribution https://github.com/Triple-T/gradle-play-publisher/issues/901)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "org.jdc.template"

    compileSdk = AppInfo.AndroidSdk.COMPILE

    defaultConfig {
        minSdk = AppInfo.AndroidSdk.MIN
        targetSdk = AppInfo.AndroidSdk.TARGET

        applicationId = AppInfo.APPLICATION_ID
        versionCode = AppInfo.Version.CODE
        versionName = AppInfo.Version.NAME

        buildConfigField("String", "BUILD_NUMBER", "\"${System.getProperty("BUILD_NUMBER")}\"")
        buildConfigField("String", "USER_AGENT_APP_NAME", "\"AndroidTemplate\"")

        // used by Room, to test migrations
        ksp {
            arg("room.schemaLocation", "$projectDir/schema")
            arg("room.incremental", "true")
            arg("room.generateKotlin", "true")
        }

        // for use with Room gradle plugin
//        room {
//
//        }

        // Integration tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-Xopt-in=kotlin.RequiresOptIn",
            "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xopt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
            "-Xopt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-Xopt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-Xopt-in=kotlin.experimental.ExperimentalTypeInference",
            "-Xopt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-Xopt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",

            // use the following to ignore enforcement version of Kotlin with Compose
            //"-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    lint {
        ignoreTestSources = true
        abortOnError = true
        disable.addAll(listOf("InvalidPackage", "DialogFragmentCallbacksDetector"))
    }

    // defined values my* in ~/.gradle/gradle.properties
    signingConfigs {
        create("upload") {
            val appUploadKeystore: String? by project
            val appUploadKeystorePassword: String by project
            val appUploadKeyAlias: String by project
            val appUploadKeyPassword: String by project

            val envSigningKeystore = System.getenv("SIGNING_KEYSTORE")
            if (appUploadKeystore != null) {
                // From Global gradle.properties (local or Jenkinsfile)
                storeFile = File(appUploadKeystore)
                storePassword = appUploadKeystorePassword
                keyAlias = appUploadKeyAlias
                keyPassword = appUploadKeyPassword
            } else if (envSigningKeystore != null){
                // From environment (local or Github Actions)
                storeFile = file(envSigningKeystore)
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            }
        }

        create("prod") {
            val appProdKeystore: String? by project
            val appProdKeystorePassword: String by project
            val appProdKeyAlias: String by project
            val appProdKeyPassword: String by project

            val envSigningKeystore = System.getenv("SIGNING_KEYSTORE")
            if (appProdKeystore != null) {
                // From Global gradle.properties (local or Jenkinsfile)
                storeFile = File(appProdKeystore)
                storePassword = appProdKeystorePassword
                keyAlias = appProdKeyAlias
                keyPassword = appProdKeyPassword
            } else if (envSigningKeystore != null){
                // From environment (local or Github Actions)
                storeFile = file(envSigningKeystore)
                storePassword = System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias = System.getenv("SIGNING_KEY_ALIAS")
                keyPassword = System.getenv("SIGNING_KEY_PASSWORD")
            }
        }
    }

    // read the "androidTemplateServiceCredentialsFile" from Gradle properties
    val androidTemplateServiceCredentialsFile: String? by project

    val buildServiceCredentialsFile = "app-distribution.json" // matches filename in Github Actions yml
    val serviceCredentialsFileFromGradle = androidTemplateServiceCredentialsFile
    val firebaseServiceCredentialsFile: String? = if (File(buildServiceCredentialsFile).exists()) buildServiceCredentialsFile else serviceCredentialsFileFromGradle
    val firebaseGroups = "mobile-dev-team, mobile-qa-team"
    val firebaseReleaseNotesFile = "commit-changelog.txt"

    buildTypes {
        val debug by getting {
            versionNameSuffix = " DEV"
            applicationIdSuffix = ".dev"
            buildConfigField("long", "BUILD_TIME", "0l") // to improve build times, do allow change on every build

            // Enable signing to test Firebase
            // signingConfig = signingConfigs.getByName("upload")
        }
        val release by getting {
            versionNameSuffix = ""
            buildConfigField("long", "BUILD_TIME", "${Date().time}l")
            signingConfig = signingConfigs.getByName("upload")

            // R8
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            firebaseAppDistribution {
                serviceCredentialsFile = firebaseServiceCredentialsFile
                groups = firebaseGroups
                releaseNotesFile = firebaseReleaseNotesFile
            }
        }
        val alpha by creating {
            initWith(release)
            versionNameSuffix = " ALPHA"
            applicationIdSuffix = ".alpha"
        }

        val beta by creating {
            initWith(release)
            versionNameSuffix = " BETA"
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
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.datastorePrefs)

    // Compose
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling.preview)
//    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.iconsext)

    // Play Service
    implementation(libs.google.play.core)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    //implementation(libs.google.firebase.perf)
    implementation(libs.google.firebase.config)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    // Code
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.datetime)
    implementation(libs.okio)
    implementation(libs.okio.assetfilesystem)

    // Inject
    implementation(libs.google.hilt.library)
    kapt(libs.google.hilt.compiler)

    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.work)

    // Android Architecture Components
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

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
    //implementation(libs.dbtools.roomSqlite)

    // Network
    implementation(libs.ktor.client.core)
//    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.resources)

    // Network
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.loggingInterceptor)

    // Logging
    implementation(libs.kermit)
    implementation(libs.kermit.crashlytics)

    // Dev
    debugImplementation(libs.leakCanary)

    // Test (Integration)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    // Test (Unit)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.engine)
    testImplementation(libs.mockK)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.assertk)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.dbtools.roomJdbc)
    testImplementation(libs.xerial.sqlite)


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

// ./gradlew jacocoTestDebugUnitTestReport
jacoco {
    toolVersion = libs.versions.jacoco.get().toString()
}

val jacocoTestReport = tasks.create("jacocoTestReport")

androidComponents.onVariants { variant ->
    val testTaskName = "test${variant.name.capitalize()}UnitTest"
    val reportTask = tasks.register("jacoco${testTaskName.capitalize()}Report", JacocoReport::class) {
        dependsOn(testTaskName)

        reports {
            xml.required.set(true)
            html.required.set(true)
        }

        classDirectories.setFrom(
            fileTree("$buildDir/tmp/kotlin-classes/${variant.name}") {
                exclude(
                    listOf(
                        // Android
                        "**/R.class",
                        "**/R\$*.class",
                        "**/BuildConfig.*",
                        "**/Manifest*.*",
                        // App Specific
                    )
                )
            }
        )

        sourceDirectories.setFrom(files("$projectDir/src/main/java", "$projectDir/src/main/kotlin"))
        executionData.setFrom(file("$buildDir/jacoco/$testTaskName.exec"))
    }

    jacocoTestReport.dependsOn(reportTask)
}

// ===== Ruler =====

// ./gradlew analyzeDebugBundle
ruler {
    abi.set("arm64-v8a")
    locale.set("en")
    screenDensity.set(375)
    sdkVersion.set(31)
}

// ===== Detekt =====

// download detekt config file
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadDetektConfig") {
    download {
        onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
        src("https://raw.githubusercontent.com/ICSEng/AndroidPublic/main/detekt/detektConfig-20230728.yml")
        dest("$projectDir/build/config/detektConfig.yml")
    }
}

// make sure when running detekt, the config file is downloaded
tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    // Target version of the generated JVM bytecode. It is used for type resolution.
    this.jvmTarget = "17"
    dependsOn("downloadDetektConfig")
}

// ./gradlew detekt
// ./gradlew detektDebug (support type checking)
detekt {
    allRules = true // fail build on any finding
    buildUponDefaultConfig = true // preconfigure defaults
    config.setFrom(files("$projectDir/build/config/detektConfig.yml")) // point to your custom config defining rules to run, overwriting default behavior
//    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    // ignore ImageVector files
    exclude("**/ui/compose/icons/**")

    reports {
        html.required.set(true) // observe findings in your browser with structure and code snippets
        xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
        txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
    }
}

// ===== TripleT / Google Play Publisher =====
play {
    // try to get the credentials from gradle properties (ex: Jenkins) OR try to pull from env.ANDROID_PUBLISHER_CREDENTIALS (Gradle Actions)
    val myServiceAccountCreds: String? by project
    myServiceAccountCreds?.let { filename ->
        serviceAccountCredentials.set(File(filename))
    }

    track.set("internal")
    defaultToAppBundles.set(true)
}

// ===== License Manager =====
// ./gradlew createLicenseReports
// ./gradlew --stacktrace -i createLicenseReports
licenseManager {
    variantName = "release"
    outputDirs = listOf("./src/main/assets", "./build/licenses")
    excludeGroups = listOf("org.dbtools")
//    invalidLicenses = listOf("GPL","GNU","NonCommercial","NoDerivatives","ShareAlike","CPAL","EPL","MPL","RPL","SPL","WTFPL","Beerware","IPA","JSON")
    invalidLicensesUrl = "https://raw.githubusercontent.com/ICSEng/AndroidPublic/main/license/invalid-licenses.json"
}
