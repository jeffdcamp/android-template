import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2")
    }
    dependencies {
        classpath(Libs.ANDROID_GRADLE_PLUGIN)
        // classpath(Plugins.Google.GRADLE_PLUGIN_KSP)
        classpath(Libs.Kotlin.GRADLE_PLUGIN)
        classpath(Libs.Kotlin.Serialization.GRADLE_PLUGIN)
        classpath(Libs.Google.Hilt.GRADLE_PLUGIN)
        classpath(Libs.AndroidX.Navigation.GRADLE_PLUGIN_SAFE_ARGS)
        classpath(Libs.Google.Firebase.GRADLE_PLUGIN_CRASHLYTICS)
        classpath(Libs.Google.Firebase.GRADLE_PLUGIN_APP_DISTRIBUTION)
        // classpath(Libs.Google.Firebase.GRADLE_PLUGIN_PERF)
        classpath(Libs.Google.GRADLE_PLUGIN_SERVICES)
        classpath(Libs.GRADLE_PLUGIN_VERSIONS)
        classpath(Libs.GRADLE_PLUGIN_PLAY_PUBLISHER)
        classpath(Libs.GRADLE_PLUGIN_LICENSE_REPORT)
        classpath(Libs.GRADLE_PLUGIN_GRADLE_DETEKT)
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Check
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
    val excludeVersionContaining = listOf("alpha", "eap") // example: "alpha", "beta"
    val ignoreArtifacts = listOf("datastore-preferences", "hilt-android", "startup-runtime") // some artifacts may be OK to check for "alpha"... add these exceptions here

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
