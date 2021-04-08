import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter() // 2021/03/15: required by detekt / Jetpack Compose (because of Kotlin Dependency)
        gradlePluginPortal()
    }
    dependencies {
        classpath(Libs.Android.GRADLE_PLUGIN)
        //classpath(Libs.Google.KSP_GRADLE_PLUGIN)
        classpath(Libs.Kotlin.GRADLE_PLUGIN)
        classpath(Libs.Kotlin.Serialization.GRADLE_PLUGIN)
        classpath(Libs.Google.Hilt.GRADLE_PLUGIN)
        classpath(Libs.AndroidX.Navigation.SAFE_ARGS_GRADLE_PLUGIN)
        classpath(Libs.Google.Firebase.CRASHLYTICS_GRADLE_PLUGIN)
        // classpath(Libs.Google.Firebase.PERF_GRADLE_PLUGIN)
        classpath(Libs.Google.SERVICES_GRADLE_PLUGIN)
        classpath(Libs.VERSIONS_GRADLE_PLUGIN)
        classpath(Libs.PLAY_PUBLISHER_GRADLE_PLUGIN)
        classpath(Libs.LICENSE_REPORT_GRADLE_PLUGIN)
        classpath(Libs.DETEKT_GRADLE_PLUGIN)
    }
}

@OptIn(ExperimentalStdlibApi::class) // to use buildList (remove with Kotlin 1.5?)
allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter() // 2021/03/15: required by detekt / Jetpack Compose (because of Kotlin Dependency)
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Check
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
    val excludeVersionContaining = listOf("alpha", "eap", "M1") // example: "alpha", "beta"
    // some artifacts may be OK to check for "alpha"... add these exceptions here
    val ignoreArtifacts = buildList {
        // early adoption items
        addAll(listOf("datastore-preferences", "lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-viewmodel-savedstate", "lifecycle-common-java8"))

        // Arctic Fox
        addAll(listOf("gradle", "viewbinding"))
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
