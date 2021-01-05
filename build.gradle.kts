import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2")
    }
    dependencies {
        classpath(BuildDeps.ANDROID)
        classpath(BuildDeps.KOTLIN)
        classpath(BuildDeps.KOTLIN_SERIALIZATION)
        classpath(BuildDeps.HILT)
        classpath(BuildDeps.SAFE_ARGS)
        classpath(BuildDeps.FIREBASE_CRASHLYTICS)
        classpath(BuildDeps.FIREBASE_APP_DISTRIBUTION)
        classpath(BuildDeps.GOOGLE_SERVICES)
        classpath(BuildDeps.GRADLE_VERSIONS)
        classpath(BuildDeps.PLAY_PUBLISHER)
        classpath(BuildDeps.LICENSE_REPORT)
        classpath(BuildDeps.GRADLE_DETEKT)
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Check
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
    val excludeVersionContaining = listOf("alpha", "eap") // example: "alpha", "beta"
    val ignoreArtifacts = listOf("material", "hilt-android", "startup-runtime") // some artifacts may be OK to check for "alpha"... add these exceptions here

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
