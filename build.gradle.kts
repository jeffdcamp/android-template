import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0-rc01")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.21.0") // version plugin support
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$KOTLIN_VERSION")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$ANDROIDX_NAVIGATION_VERSION")
//        classpath("com.google.gms:oss-licenses:$PLAYSERVICE_LICENSE_VERSION")
    }
}

allprojects {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://jitpack.io") // ihsanbal:LoggingInterceptor
        jcenter()
//        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
    }

    // Gradle Dependency Check
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
    val excludeVersionContaining = listOf("alpha", "eap") // example: "alpha", "beta"
    val ignoreArtifacts = listOf("material", "appcompat") // some artifacts may be OK to check for "alpha"... add these exceptions here

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
