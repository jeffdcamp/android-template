
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import de.undercouch.gradle.tasks.download.Download
import io.gitlab.arturbosch.detekt.Detekt

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
//        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        classpath(libs.ruler.gradlePluginClasspath)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.android.kotlin.multiplatform.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.gms) apply false // must be defined BEFORE firebase
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.licenseManager) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.versions)
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.download)
}

// ===== Gradle Dependency Check =====
// ./gradlew dependencyUpdates -Drevision=release
// ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
//
// ./gradlew app:dependencyInsight --configuration debugRuntimeClasspath --dependency androidx.room
// ./gradlew shared:dependencyInsight --configuration commonMainApiDependenciesMetadata --dependency androidx.room
// ./gradlew shared:resolvableConfigurations | grep "^Configuration"
tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isNonStable(
            version = candidate.version,
            includeStablePreRelease = true
        )
    }
}

fun isNonStable(version: String, includeStablePreRelease: Boolean): Boolean {
    val stablePreReleaseKeyword = listOf("RC", "BETA").any { version.uppercase().contains(it) }
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    val isStable = if (includeStablePreRelease) {
        stableKeyword || regex.matches(version) || stablePreReleaseKeyword
    } else {
        stableKeyword || regex.matches(version)
    }
    return isStable.not()
}

allprojects {
    // ===== Detekt =====
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.download.get().pluginId)

    // download detekt config file
    tasks.register<Download>("downloadDetektConfig") {
        download {
            onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
            src("https://mobile-cdn.churchofjesuschrist.org/android/build/detekt/detektConfig-20231101.yml")
            dest("$projectDir/build/config/detektConfig.yml")
        }
    }

    // make sure when running detekt, the config file is downloaded
    tasks.withType<Detekt>().configureEach {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        this.jvmTarget = "17"
        dependsOn("downloadDetektConfig")
    }

    // ./gradlew detekt
    // ./gradlew detektDebug (support type checking)
    detekt {
        source.setFrom("src/main/kotlin", "src/commonMain/kotlin", "src/desktopMain/kotlin", "src/androidMain/kotlin")
        allRules = true // fail build on any finding
        buildUponDefaultConfig = true // preconfigure defaults
        config.setFrom(files("$projectDir/build/config/detektConfig.yml")) // point to your custom config defining rules to run, overwriting default behavior
        //    baseline = file("$projectDir/config/baseline.xml") // a way of suppressing issues before introducing detekt
    }

    tasks.withType<Detekt>().configureEach {
        // ignore ImageVector files
        exclude("**/ui/compose/icons/**")

        reports {
            html.required.set(true) // observe findings in your browser with structure and code snippets
            xml.required.set(true) // checkstyle like format mainly for integrations like Jenkins
            txt.required.set(true) // similar to the console output, contains issue signature to manually edit baseline files
        }
    }
}

// ===== Dependency Analysis =====
// ./gradlew app:projectHealth
dependencyAnalysis {
    issues {
        all {
            onAny {
                severity("fail")
            }
            onUnusedDependencies {
                exclude(
                    depGroupAndName(libs.leakCanary),
                    "androidx.test:core" // work around for supporting tests on Android 33 devices (https://issuetracker.google.com/issues/240993946) till ui-test-junit4 updates its dependencies (fixed with ui-test:1.4.0-alpha03+)
                )
            }
            onUsedTransitiveDependencies { severity("ignore") }
            onIncorrectConfiguration { severity("ignore") }
            onCompileOnly { severity("ignore") }
            onRuntimeOnly { severity("ignore") }
            onUnusedAnnotationProcessors {
                exclude(
                    depGroupAndName(libs.google.hilt.compiler)
                )
            }
        }
    }
}

fun depGroupAndName(dependency: Provider<MinimalExternalModuleDependency>): String {
    return dependency.get().let { "${it.group}:${it.name}" }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
    delete(rootProject.file(".kotlin"))
}
