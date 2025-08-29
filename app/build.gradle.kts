plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.navigationSafeArgs)
    kotlin("android")
}

android {
    namespace = "dev.sergiobelda.marvel"

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "dev.sergiobelda.marvel"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlin {
        jvmToolchain(17)
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(projects.common)
    implementation(projects.data)
    implementation(projects.domain)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedataKtx)
    implementation(libs.androidx.lifecycle.runtimeKtx)
    implementation(libs.androidx.lifecycle.viewmodelKtx)
    implementation(libs.androidx.navigation.navigationFragmentKtx)
    implementation(libs.androidx.navigation.navigationUiKtx)
    implementation(libs.androidx.paging.pagingRuntimeKtx)
    implementation(libs.coil3.coil)
    implementation(libs.coil3.networkOkhttp)
    implementation(libs.google.material)
    implementation(libs.google.dagger.hiltAndroid)
    ksp(libs.google.dagger.hiltAndroidCompiler)
    implementation(libs.kotlin.coroutinesAndroid)
    implementation(libs.kotlin.coroutinesCore)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutinesTest)
    testImplementation(libs.mockk.mockk)

    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.coreKtx)
    androidTestImplementation(libs.androidx.test.espresso.espressoCore)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.ext.junitKtx)
    androidTestImplementation(libs.kotlin.coroutinesTest)
}
