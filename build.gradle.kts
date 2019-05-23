buildscript {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0-beta02")
        classpath("com.github.ben-manes:gradle-versions-plugin:0.21.0") // version plugin support
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$ANDROIDX_NAVIGATION_VERSION")
//        classpath("com.google.gms:oss-licenses:$PLAYSERVICE_LICENSE_VERSION")
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
