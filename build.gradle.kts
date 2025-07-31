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

allprojects {
    // Gradle Dependency Reports
    // ./gradlew -q app:dependencies --configuration debugCompileClasspath > deps.txt
    // ./gradlew app:dependencies --scan.

    // Gradle Dependency Check
    // ./gradlew dependencyUpdates -Drevision=release
    // ./gradlew dependencyUpdates -Drevision=release --refresh-dependencies
    apply(plugin = rootProject.libs.plugins.versions.get().pluginId)
    val excludeVersionContaining = listOf("alpha", "eap", "M1", "dev") // example: "alpha", "beta"
    // some artifacts may be OK to check for "alpha"... add these exceptions here
    val ignoreArtifacts = buildList {
        addAll(listOf("room-compiler"))

        // Compose
//        addAll(listOf("material3", "ui", "ui-tooling-preview", "ui-test-junit4", "ui-test-manifest", "material3-window-size-class", "compiler"))
//        addAll(listOf("window")) // material3 uses latest 1.1.0-alpha
    }

    tasks.named<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask>("dependencyUpdates") {
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

    // ===== Detekt =====
    apply(plugin = rootProject.libs.plugins.detekt.get().pluginId)
    apply(plugin = rootProject.libs.plugins.download.get().pluginId)

    // download detekt config file
    tasks.register<de.undercouch.gradle.tasks.download.Download>("downloadDetektConfig") {
        download {
            onlyIf { !file("$projectDir/build/config/detektConfig.yml").exists() }
            src("https://mobile-cdn.churchofjesuschrist.org/android/build/detekt/detektConfig-20231101.yml")
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
        source.setFrom("src/main/kotlin", "src/commonMain/kotlin", "src/desktopMain/kotlin", "src/androidMain/kotlin")
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
    delete(rootProject.buildDir)
}
