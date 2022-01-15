import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

val publicApiKey: String = gradleLocalProperties(rootDir).getProperty("public_api_key")
val privateApiKey: String = gradleLocalProperties(rootDir).getProperty("private_api_key")

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
        debug {
            buildConfigField("String", "PUBLIC_API_KEY", publicApiKey)
            buildConfigField("String", "PRIVATE_API_KEY", privateApiKey)
        }
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
        freeCompilerArgs += "-Xjvm-default=all"
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

    with(Libs.AndroidX.Lifecycle) {
        implementation(liveData)
        implementation(viewModel)
    }

    with(Libs.AndroidX.Navigation) {
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
    }

    implementation(Libs.Google.Material.materialComponents)

    with(Libs.SquareUp.Moshi) {
        implementation(moshi)
        kapt(moshiKotlinCodegen)
    }
    with(Libs.SquareUp.OkHttp3) {
        implementation(okhttp)
        implementation(loggingInterceptor)
    }
    with(Libs.SquareUp.Retrofit2) {
        implementation(converterMoshi)
        implementation(converterScalars)
        implementation(retrofit)
    }

    with(Libs.Google.Dagger) {
        implementation(hilt)
        kapt(hiltCompiler)
    }

    implementation(Libs.coil)

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
