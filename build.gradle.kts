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
        classpath(libs.playPublisherGradlePluginClasspath) // place BEFORE app distribution (if app distribution is used)
        classpath(libs.google.firebase.appDistGradlePluginClasspath)
        classpath(libs.google.servicesgradlePluginClasspath)
        classpath(libs.gradleVersions.gradlePluginClasspath)
        classpath(libs.dbtools.licenseManager.gradlePluginClasspath)
        classpath(libs.ruler.gradlePluginClasspath)
    }
}

@OptIn(ExperimentalStdlibApi::class) // to use buildList (remove with Kotlin 1.5?)
allprojects {
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

        // Compose
        addAll(listOf("material3"))
        addAll(listOf("window")) // material3 uses latest 1.1.0-alpha
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
