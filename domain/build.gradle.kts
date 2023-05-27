plugins {
    alias(libs.plugins.androidLibrary)
    kotlin("android")
}

android {
    namespace = "dev.sergiobelda.marvel.domain"

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.paging.pagingRuntimeKtx)
    implementation(libs.kotlin.coroutinesCore)

    testImplementation(libs.androidx.paging.pagingCommon)
    testImplementation(libs.kotlin.coroutinesTest)
    testImplementation(libs.junit)
    testImplementation(libs.mockk.mockk)
}
