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
//        classpath(libs.google.kspGradlePluginClasspath)
        classpath(libs.kotlin.gradlePluginClasspath)
        classpath(libs.google.hilt.gradlePluginClasspath)
        classpath(libs.androidx.navigation.safeArgsGradlePluginClasspath)
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
            // 2.7.0+ alpha is required when targeting SDK 31
            "work-runtime-ktx", "work-gcm",

            "lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-viewmodel-savedstate", "lifecycle-common-java8", "lifecycle-process",
            "navigation-fragment-ktx", "navigation-ui-ktx", "navigation-safe-args-gradle-plugin",
            "fragment-ktx"
        ))
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
