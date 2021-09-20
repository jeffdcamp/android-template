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
        classpath(libs.google.servicesgradlePluginClasspath)
        classpath(libs.gradleVersions.gradlePluginClasspath)
        classpath(libs.dbtools.licenseManager.gradlePluginClasspath)
    }
}

@OptIn(ExperimentalStdlibApi::class) // to use buildList (remove with Kotlin 1.5?)
allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Check
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
    val excludeVersionContaining = listOf("alpha", "eap", "M1") // example: "alpha", "beta"
    // some artifacts may be OK to check for "alpha"... add these exceptions here
    val ignoreArtifacts = buildList {
        // early adoption items
        addAll(listOf(
            "lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-viewmodel-savedstate", "lifecycle-common-java8", "lifecycle-process",
            "navigation-fragment-ktx", "navigation-ui-ktx", "navigation-safe-args-gradle-plugin",
            "fragment-ktx"
        ))
        addAll(listOf("datastore-preferences", "lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-viewmodel-savedstate", "lifecycle-common-java8"))

//        // Arctic Fox
//        addAll(listOf("gradle", "viewbinding"))

        // SDK 31 / Android 12
        // 2.7.0+ alpha is required when targeting SDK 31 AND running on Android 12 device
        addAll(listOf("work-runtime-ktx", "work-gcm"))

        // Room (2.4.0+ to support ksp)
        addAll(listOf("room-compiler", "room-ktx", "room-runtime"))

        // Compose Core (1.1.0+ to support ksp)
        addAll(listOf("compiler", "ui", "ui-tooling", "ui-util", "activity-ktx", "material"))

        // Compose Extras
        addAll(listOf("navigation-compose", "constraintlayout-compose", "activity-compose", "paging-compose", "activity-ktx"))
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
