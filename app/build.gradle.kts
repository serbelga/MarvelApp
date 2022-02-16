import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

val publicApiKey: String = gradleLocalProperties(rootDir).getProperty("public_api_key") ?: "\"\""
val privateApiKey: String = gradleLocalProperties(rootDir).getProperty("private_api_key") ?: "\"\""

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
            buildConfigField("String", "PUBLIC_API_KEY", publicApiKey)
            buildConfigField("String", "PRIVATE_API_KEY", privateApiKey)
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

    implementation(project(":common"))
    implementation(project(":domain"))

    ktlint(Libs.ktLint)

    implementation(Libs.kotlinCoroutinesAndroid)
    implementation(Libs.kotlinCoroutinesCore)
    testImplementation(Libs.kotlinCoroutinesTest)
    androidTestImplementation(Libs.kotlinCoroutinesTest)

    with(Libs.AndroidX) {
        implementation(appCompat)
        implementation(constraintLayout)
        implementation(coreKtx)
    }

    with(Libs.AndroidX.Lifecycle) {
        implementation(liveData)
        implementation(runtime)
        implementation(viewModel)
        androidTestImplementation(archCoreTesting)
    }

    with(Libs.AndroidX.Navigation) {
        implementation(navigationFragmentKtx)
        implementation(navigationUiKtx)
    }

    implementation(Libs.AndroidX.paging3)

    implementation(Libs.Google.Material.materialComponents)

    implementation(Libs.coil)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.Test.espressoCore)
    androidTestImplementation(Libs.Test.extJunit)

    testImplementation(Libs.mockk)

    with(Libs.AndroidX.Test) {
        androidTestImplementation(core)
        androidTestImplementation(coreKtx)
        androidTestImplementation(espressoCore)
        androidTestImplementation(extJunit)
        androidTestImplementation(extJunitKtx)
    }
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
