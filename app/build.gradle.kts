plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "dev.sergiobelda.marvel"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

val ktlint: Configuration by configurations.creating

dependencies {

    ktlint(Libs.ktLint)

    with(Libs.AndroidX) {
        implementation(appCompat)
        implementation(constraintLayout)
        implementation(coreKtx)
    }

    with(Libs.AndroidX.Navigation) {
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
    }

    implementation(Libs.Google.Material.materialComponents)

    with(Libs.Retrofit) {
        implementation(converterMoshi)
        implementation(converterScalars)
        implementation(retrofit)
    }

    with(Libs.Google.Dagger) {
        implementation(hilt)
        kapt(hiltCompiler)
    }

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.Test.espressoCore)
    androidTestImplementation(Libs.Test.extJunit)
}

task("ktlint", JavaExec::class) {
    group = "verification"
    main = "com.pinterest.ktlint.Main"
    classpath = configurations.getByName("ktlint")
    args = listOf("src/**/*.kt")
}

val check by tasks
check.dependsOn(tasks.getByName("ktlint"))

task("ktlintFormat", JavaExec::class) {
    group = "formatting"
    main = "com.pinterest.ktlint.Main"
    classpath = configurations.getByName("ktlint")
    args = listOf("-F", "src/**/*.kt")
}
