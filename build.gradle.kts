import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        // workaround for no support of "libs" in buildscript (https://github.com/gradle/gradle/issues/16958#issuecomment-827140071) (https://discuss.gradle.org/t/trouble-using-centralized-dependency-versions-in-buildsrc-plugins-and-buildscript-classpath/39421)
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs") as org.gradle.accessors.dm.LibrariesForLibs

        classpath(libs.android.gradlePlugin)
        //classpath(Libs.Google.KSP_GRADLE_PLUGIN)
        classpath(libs.kotlin.gradlePlugin)
        classpath(libs.kotlin.serialization.gradlePlugin)
        classpath(libs.google.hilt.gradlePlugin)
        classpath(libs.androidx.navigation.safeArgsGradlePlugin)
        classpath(libs.google.firebase.crashlyticsGradlePlugin)
        // classpath(libs.google.firebase.perfGradlePlugin)
        classpath(libs.google.servicesgradlePlugin)
        classpath(libs.gradleVersions.gradlePlugin)
        classpath(libs.playPublisher.gradlePlugin)
        classpath(libs.detekt.gradlePlugin)
        classpath(libs.dbtools.licenseManager)
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
        addAll(listOf("lifecycle-runtime-ktx", "lifecycle-viewmodel-ktx", "lifecycle-viewmodel-savedstate", "lifecycle-common-java8"))
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
