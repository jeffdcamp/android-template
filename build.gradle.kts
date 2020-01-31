import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.fabric.io/public")
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.0-rc02")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.27.0") // version plugin support
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$KOTLIN_VERSION")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$ANDROIDX_NAVIGATION_VERSION")
        classpath("com.google.firebase:firebase-appdistribution-gradle:1.3.1")
        classpath("com.google.gms:google-services:4.3.3")
        classpath("io.fabric.tools:gradle:1.31.2")
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
    val ignoreArtifacts = listOf("material") // some artifacts may be OK to check for "alpha"... add these exceptions here

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
