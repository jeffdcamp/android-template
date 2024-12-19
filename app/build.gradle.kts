@file:Suppress("UnstableApiUsage")

import java.util.Date

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.gms)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.appdistribution)
//    alias(libs.plugins.firebase.perf)
    alias(libs.plugins.download)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kover)
    alias(libs.plugins.room)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
    alias(libs.plugins.playPublisher)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.licenseManager)
    alias(libs.plugins.dependencyAnalysis)
    id("com.spotify.ruler")
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

        room {
            schemaDirectory("$projectDir/schema")
        }

        ksp {
            // options that are not yet in the Room Gradle plugin
            // https://developer.android.com/jetpack/androidx/releases/room#gradle-plugin
            arg("room.incremental", "true")
            arg("room.generateKotlin", "true")
        }

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

    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-opt-in=androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi",
            "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
            "-opt-in=kotlin.experimental.ExperimentalTypeInference",
            "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi",
            "-opt-in=androidx.compose.material3.adaptive.navigationsuite.ExperimentalMaterial3AdaptiveNavigationSuiteApi",

            // use the following to ignore enforcement version of Kotlin with Compose
            //"-P", "plugin:androidx.compose.compiler.plugins.kotlin:suppressKotlinVersionCompatibilityCheck=true"
        )
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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.datastorePrefs)

    // Compose
    implementation(libs.compose.ui)
    debugImplementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material3.adaptive)
    implementation(libs.compose.material3.adaptive.navigation)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.compose.material.iconsext)

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

    // Inject
    implementation(libs.google.hilt.library)
    ksp(libs.google.hilt.compiler)

    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.work)

    // Android Architecture Components
    implementation(libs.androidx.lifecycle.compose)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // WorkManager
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.work.gcm)
    implementation(libs.workmanagertools)

    // Database
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)
    implementation(libs.dbtools.room)

    // Custom SQLite database
    // (for use of SqliteOrgSQLiteOpenHelperFactory)
    //implementation(libs.dbtools.roomSqlite)

    // Network
    implementation(libs.ktor.client.core)
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
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockK)
    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.assertk)
    testImplementation(libs.konsist)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.dbtools.roomJdbc)
    testImplementation(libs.turbine)
    testImplementation(libs.xerial.sqlite)
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
                    "*hilt_aggregated_deps*",
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
                    "*_HiltModules*",
                    "*_Impl*",
                    "*ComposableSingletons*",
                    "*Hilt*",
                    "*Initializer*",

                    // App Specific
                    "*MainAppScaffoldWithNavBarKt*"
                )

                annotatedBy(
                    "*Composable*",
                    "*HiltAndroidApp*",
                    "*HiltViewModel*",
                    "*HiltWorker*",
                    "*AndroidEntryPoint*",
                    "*Module*",
                    "*SuppressCoverage*",
                )
            }
        }

        verify {
            rule {
                minBound(25) // minimum percent coverage without failing build (Line percent)
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

// ===== Detekt =====

// download detekt config file
tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadDetektConfig") {
    download {
        onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
        src("https://raw.githubusercontent.com/ICSEng/AndroidPublic/main/detekt/detektConfig-20231101.yml")
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
