buildscript {
    val kotlinVersion = "1.3.30"

    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0-alpha12")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.21.0") // version plugin support
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.0.0")
//        classpath("com.google.gms:oss-licenses:0.9.1")
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
    apply(plugin = "com.github.ben-manes.versions") // ./gradlew dependencyUpdates -Drevision=release
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
