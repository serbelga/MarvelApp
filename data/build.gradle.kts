plugins {
    id("com.android.library")
    id("com.google.devtools.ksp")
    kotlin("android")
    kotlin("kapt")
}

val publicApiKey: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
    rootDir
).getProperty("public_api_key") ?: "\"\""
val privateApiKey: String = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
    rootDir
).getProperty("private_api_key") ?: "\"\""

android {
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
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
