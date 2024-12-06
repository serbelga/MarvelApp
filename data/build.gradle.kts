import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    kotlin("android")
    kotlin("kapt")
}

val publicApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("public_api_key") ?: "\"\""
val privateApiKey: String = gradleLocalProperties(rootDir, providers).getProperty("private_api_key") ?: "\"\""

android {
    namespace = "dev.sergiobelda.marvel.data"

    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "PUBLIC_API_KEY", publicApiKey)
            buildConfigField("String", "PRIVATE_API_KEY", privateApiKey)
        }
        release {
            buildConfigField("String", "PUBLIC_API_KEY", publicApiKey)
            buildConfigField("String", "PRIVATE_API_KEY", privateApiKey)
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

    implementation(projects.common)
    implementation(projects.domain)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.paging.pagingRuntimeKtx)
    implementation(libs.androidx.room.roomKtx)
    implementation(libs.androidx.room.roomPaging)
    implementation(libs.androidx.room.roomRuntime)
    ksp(libs.androidx.room.roomCompiler)
    implementation(libs.google.dagger.hiltAndroid)
    kapt(libs.google.dagger.hiltAndroidCompiler)
    implementation(libs.kotlin.coroutinesCore)
    implementation(libs.squareup.moshi.moshi)
    kapt(libs.squareup.moshi.moshiKotlinCodegen)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.okhttp3.loggingInterceptor)
    implementation(libs.squareup.retrofit2.converterMoshi)
    implementation(libs.squareup.retrofit2.converterScalars)
    implementation(libs.squareup.retrofit2.retrofit)

    testImplementation(libs.androidx.paging.pagingCommon)
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutinesTest)
    testImplementation(libs.mockk.mockk)

    androidTestImplementation(libs.androidx.room.roomTesting)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espressoCore)
    androidTestImplementation(libs.kotlin.coroutinesTest)
}
