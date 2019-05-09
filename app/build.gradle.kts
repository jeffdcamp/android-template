import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.io.FileInputStream
import java.util.Date
import java.util.Properties

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
val appVersionCode = readVersionCode()
val appVersionName = "1.0.0 ($appVersionCode.${System.getProperty("BUILD_NUMBER")})"

kapt {
    javacOptions {
        // Increase the max count of errors from annotation processors. (Default is 100)
        option("-Xmaxerrs", 500)
    }
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(28)

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
        val myKeystore = "" // : String = "" by project
        val myKeystorePassword = "" // : String = "" by project
        val myKeyAlias = "" // : String = "" by project
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

object Versions {
    const val COROUTINES = "1.2.1"
    const val ROOM = "2.1.0-alpha07"
    const val DBTOOLS_ROOM = "4.8.0"
    const val DAGGER = "2.22.1"
    const val OKHTTP = "3.14.1"
}

dependencies {
    // Android
    implementation("androidx.appcompat:appcompat:1.0.2")
    implementation("androidx.recyclerview:recyclerview:1.0.0")
    implementation("androidx.preference:preference-ktx:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.annotation:annotation:1.0.2") // includes support-v4
    implementation("androidx.multidex:multidex:2.0.1")
    implementation("androidx.multidex:multidex-instrumentation:2.0.0")
    implementation("com.google.android.gms:play-services-analytics:16.0.8")
//    implementation("com.google.android.gms:play-services-oss-licenses:$playServicesVersion")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("androidx.core:core-ktx:1.0.1")

    // Code
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${KotlinCompilerVersion.VERSION}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.COROUTINES}")
    implementation("me.eugeniomarletti:android-extras-delegates:1.0.5")
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.0")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.vikingsen.inject:viewmodel-inject:0.1.1")
    kapt("com.vikingsen.inject:viewmodel-inject-processor:0.1.1")

    // UI
    implementation("com.afollestad.material-dialogs:core:2.8.1")
    implementation("com.afollestad.material-dialogs:datetime:2.8.1")
    implementation("com.afollestad.material-dialogs:lifecycle:2.8.1")

    // === Android Architecture Components ===
    implementation("androidx.lifecycle:lifecycle-extensions:2.0.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.0.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.0.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.0.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.0.1")

    // Database
    implementation("androidx.room:room-runtime:${Versions.ROOM}")
    implementation("androidx.room:room-ktx:${Versions.ROOM}")
    kapt("androidx.room:room-compiler:${Versions.ROOM}")
    implementation("org.dbtools:dbtools-room:${Versions.DBTOOLS_ROOM}")

    // Debug Database (emulator: adb forward tcp:8080 tcp:8080) (https://github.com/amitshekhariitbhu/Android-Debug-Database)
    // debugimplementation("com.amitshekhar.android:debug-db:1.0.4")

    // Network
    implementation("com.squareup.okhttp3:okhttp:${Versions.OKHTTP}")
    implementation("com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}")

    // JSON Parsing
    implementation("com.google.code.gson:gson:2.8.5")
    implementation("com.squareup.retrofit2:converter-gson:2.5.0")

    // Dagger 2
    implementation("com.google.dagger:dagger:${Versions.DAGGER}")
    kapt("com.google.dagger:dagger-compiler:${Versions.DAGGER}")

    // Custom SQLite database

    // (for use of SqliteOrgSQLiteOpenHelperFactory in AppModule.kt)
//    implementation("org.dbtools:dbtools-room:$dbtoolsRoomVersion")
//    implementation("org.dbtools:dbtools-room-sqliteorg:$dbtoolsRoomVersion")

//    implementation("org.sqlite.sqliteX:sqlite-android:3.15.2")

//    implementation("org.sqlite:sqlite-android:3.8.9.0-20150415")
//    implementation("net.sqlcipher:sqlcipher-aar:3.1.0.20140823")

    // Test (Integration)
    // Espresso core dependencies
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.1")
    androidTestImplementation("androidx.test:runner:1.1.1")
    androidTestImplementation("androidx.annotation:annotation:1.0.2")// fix dependency conflict warning

    // Espresso contrib dependencies
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.1.1")
    androidTestImplementation("androidx.test:rules:1.1.1")
    androidTestImplementation("androidx.appcompat:appcompat:1.0.2")
    androidTestImplementation("androidx.recyclerview:recyclerview:1.0.0")
    androidTestImplementation("com.google.android.material:material:1.0.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.0")

    // Test (Unit)
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.1")
    // testimplementation("org.junit.vintage:junit-vintage-engine:5.4.0" // junit 4 support (may be needed for Espresso)
    testImplementation("org.mockito:mockito-core:2.27.0") // be sure to add src/test/resources/mockito-extensions (so you don"t have to "open" all of your classes)
    testImplementation("com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP}")
    testImplementation("org.threeten:threetenbp:1.3.8")
    testImplementation("org.xerial:sqlite-jdbc:3.27.2.1")
    testImplementation("androidx.room:room-testing:${Versions.ROOM}")
    testImplementation("org.dbtools:dbtools-room-jdbc:${Versions.DBTOOLS_ROOM}")
    kaptTest("com.google.dagger:dagger-compiler:${Versions.DAGGER}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.COROUTINES}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
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

/**
 * Read the versionCode for a specific app.
 *
 * Be sure to define the following ABOVE defaultConfig {...}:
 * ext.versionCodeAppName = "my-app"
 * ext.minVersionCode = 100
 *
 * Set versionCode in defaultConfig {...}:
 * versionCode readVersionCode()
 *
 * @param increment Increments the version code (NOTE: This will increment AFTER configuration is executed (after defaultConfig {...}) (next build will have the incremented value)
 * @return current versionCode
 */
fun readVersionCode(increment: Boolean = false): Int {
    val versionsFilename = "$versionCodeAppName.properties"
    val versionCodesDirname = "${System.getProperty("user.home")}/.app-version-codes"

    // prepare directory
    val versionsDir = File(versionCodesDirname)
    if (!versionsDir.exists() && !versionsDir.mkdirs()) {
        throw IllegalStateException("Cannot create versions directory [${versionsDir.absolutePath}]")
    }

    // read existing versions file (create if does not exist)
    val versionPropsFile = File(versionsDir, versionsFilename)
    val versionProps = Properties()
    if (versionPropsFile.canRead()) {
        val reader = FileInputStream(versionPropsFile)
        versionProps.load(reader)
        reader.close()
    } else {
        println("Failed to read properties file [${versionPropsFile.absolutePath}]")
        versionProps["VERSION_CODE"] = "$minVersionCode"
    }

    // get the existing version
    var versionCode = (versionProps["VERSION_CODE"] as String).toInt()

    // make sure the version code is GREATER THAN the minVersionCode
    if (versionCode < minVersionCode) {
        versionCode = minVersionCode
    }

    println("Current build will use versionCode [$versionCode]")

    if (increment) {
        // increment version code
        versionCode++

        // write updated version code
        versionPropsFile.writeText("VERSION_CODE=$versionCode")

        println("Incremented versionCode to [$versionCode]")
    }

    return versionCode
}

tasks.register("incrementVersionCode") {
    doLast {
        readVersionCode(true)
    }
}
