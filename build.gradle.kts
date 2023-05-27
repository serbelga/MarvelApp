plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHilt) apply false
    alias(libs.plugins.kotlin) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.navigationSafeArgs) apply false
}

subprojects {
    apply {
        from("${rootDir}/ktlint.gradle.kts")
    }
}
