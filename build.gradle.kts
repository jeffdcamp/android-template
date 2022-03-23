import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.android.gradlePluginClasspath)
        classpath(libs.kotlin.gradlePluginClasspath)
        classpath(libs.google.hilt.gradlePluginClasspath)
        classpath(libs.google.firebase.crashlyticsGradlePluginClasspath)
        // classpath(libs.google.firebase.perfGradlePluginClasspath)
        classpath(libs.google.firebase.appDistGradlePluginClasspath)
        classpath(libs.google.servicesgradlePluginClasspath)
        classpath(libs.gradleVersions.gradlePluginClasspath)
        classpath(libs.dbtools.licenseManager.gradlePluginClasspath)
        classpath(libs.ruler.gradlePluginClasspath)
    }
}

plugins {
    // Work-around to fix conflict with "Firebase AppDistribution" and "Triple-T" (both depend on different 'com.google.http-client')
    // - Be sure to match this version to Triple-T version "android-publisher" in https://github.com/Triple-T/gradle-play-publisher/blob/master/settings.gradle.kts
    // - Tiple-T issue https://github.com/Triple-T/gradle-play-publisher/issues/901
    id("com.github.triplet.play") version "3.7.0" apply false
}

@OptIn(ExperimentalStdlibApi::class) // to use buildList (remove with Kotlin 1.5?)
allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Reports
    // ./gradlew -q app:dependencies --configuration debugCompileClasspath > deps.txt
    // ./gradlew app:dependencies --scan.

    // Gradle Dependency Check
    // ./gradlew dependencyUpdates -Drevision=release
    // ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
    apply(plugin = "com.github.ben-manes.versions")
    val excludeVersionContaining = listOf("alpha", "eap", "M1") // example: "alpha", "beta"
    // some artifacts may be OK to check for "alpha"... add these exceptions here
    val ignoreArtifacts = buildList {
        addAll(listOf("core-splashscreen"))

        // Compose Core (1.1.0+ to support ksp)
        addAll(listOf("compiler", "ui", "ui-tooling", "ui-util", "activity-ktx", "material"))

        // Compose Extras
        addAll(listOf("navigation-compose", "constraintlayout-compose", "activity-compose", "paging-compose", "activity-ktx"))
        // Compose Test
        addAll(listOf("ui-test-junit4", "ui-test-manifest"))
    }

    tasks.named<DependencyUpdatesTask>("dependencyUpdates") {
        resolutionStrategy {
            componentSelection {
                all {
                    if (ignoreArtifacts.contains(candidate.module).not()) {
                        val rejected = excludeVersionContaining.any { qualifier ->
                            candidate.version.matches(Regex("(?i).*[.-]$qualifier[.\\d-+]*"))
                        }
                        if (rejected) {
                            reject("Release candidate")
                        }
                    }
                }
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
