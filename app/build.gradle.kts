@file:Suppress("UnstableApiUsage")

import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android) // remove when android.builtInKotlin=true (or android.builtInKotlin is removed)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appdistribution)
//    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.playPublisher)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.licenseManager)
    alias(libs.plugins.dependencyAnalysis)
    id("com.spotify.ruler")
}

kotlin {
    jvmToolchain(JavaVersion.VERSION_17.majorVersion.toInt())
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
        optIn.add("kotlin.uuid.ExperimentalUuidApi")
        optIn.add("kotlinx.coroutines.ExperimentalCoroutinesApi")
        optIn.add("androidx.compose.material3.ExperimentalMaterial3Api")
        optIn.add("androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi")
        freeCompilerArgs.addAll(
            "-Xexplicit-backing-fields", // Added with Kotlin 2.3
            "-Xreturn-value-checker=check" // Added with Kotlin 2.3 (https://kotlinlang.org/docs/unused-return-value-checker.html#mark-functions-to-check-ignored-results)
        )
    }
}

android {
    namespace = "org.jdc.template"

    compileSdk = AppInfo.AndroidSdk.COMPILE

    defaultConfig {
        minSdk = AppInfo.AndroidSdk.MIN
        targetSdk = AppInfo.AndroidSdk.TARGET

        applicationId = AppInfo.APPLICATION_ID
        versionCode = AppInfo.Version.VERSION_CODE
        versionName = AppInfo.Version.NAME

        buildConfigField("String", "USER_AGENT_APP_NAME", "\"AndroidTemplate\"")

        // Integration tests
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources.excludes.add("META-INF/versions/**")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    androidResources {
        generateLocaleConfig = true
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
            } else if (envSigningKeystore != null) {
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
            } else if (envSigningKeystore != null) {
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

            //noinspection WrongGradleMethod
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
}

dependencies {
    implementation(project(":shared"))

    // Android
    coreLibraryDesugaring(libs.android.desugar)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.datastorePrefs)

    // Compose
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.adaptive)
    implementation(libs.compose.material3.adaptive.navigation)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.iconsext)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.config)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.analytics)

    // Code
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.atomicfu)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlin.datetime)
    implementation(libs.okio)

    // Inject
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)
    implementation(libs.koin.androidx.startup)
    implementation(libs.koin.androidx.workmanager)

    // Android Architecture Components
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Navigation
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.gcm)

    // Network
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.resources)

    // Paging
    implementation(libs.androidx.paging.compose)

    // Logging
    implementation(libs.kermit)
    implementation(libs.kermit.crashlytics)

    // Dev
    debugImplementation(libs.leakCanary)

    // Test (Integration)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    // Test (Unit)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockK)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.assertk)
    testImplementation(libs.konsist)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.turbine)
}

// ===== TEST TASKS =====

// create JUnit reports
tasks.withType<Test> {
    useJUnitPlatform()
}

// ===== Kover (JUnit Coverage Reports) =====
// ./gradlew koverHtmlReportDebug
// ./gradlew koverXmlReportDebug
// ./gradlew koverVerifyDebug
kover {
    reports {
        filters {
            excludes {
                packages(
                    "*codegen*",

                    // App Specific
                    "org.jdc.template.ui",
                )

                classes(
                    "*Fragment",
                    "*Fragment\$*",
                    "*Activity",
                    "*Activity\$*",
                    "*.databinding.*",
                    "*.BuildConfig",
                    "*Factory",
                    "*_Impl*",
                    "*ComposableSingletons*",
                    "*Initializer*",

                    // App Specific
                    "*MainAppScaffoldWithNavBarKt*"
                )

                annotatedBy(
                    "*Composable*",
                    "*Module*",
                    "*SuppressCoverage*",
                )
            }
        }

        verify {
            rule {
                minBound(4) // minimum percent coverage without failing build (Line percent)
            }
        }
    }
}

// ===== Ruler =====

// ./gradlew analyzeDebugBundle
ruler {
    abi.set("arm64-v8a")
    locale.set("en")
    screenDensity.set(375)
    sdkVersion.set(31)
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
    invalidLicenses = listOf("GPL","GNU","NonCommercial","NoDerivatives","ShareAlike","CPAL","EPL","MPL","RPL","SPL","WTFPL","Beerware","IPA","JSON","APSL","Artistic")
//    invalidLicensesUrl = "https://xxxx/license/invalid-licenses.json"
}
