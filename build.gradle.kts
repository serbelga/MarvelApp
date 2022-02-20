buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.gradlePluginz)
        classpath(libs.kotlin.gradlePluginz)
        classpath(libs.ksp.gradlePluginz)
        classpath(libs.google.dagger.hiltGradlePluginz)
        classpath(libs.androidx.navigation.navigationSafeArgsPluginz)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects {
    apply {
        from("${rootDir}/ktlint.gradle.kts")
    }
}
