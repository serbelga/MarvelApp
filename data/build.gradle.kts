plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 24
        targetSdk = 31

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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":common"))
    implementation(project(":domain"))

    implementation(Libs.kotlinCoroutinesCore)
    testImplementation(Libs.kotlinCoroutinesTest)
    androidTestImplementation(Libs.kotlinCoroutinesTest)

    implementation(Libs.AndroidX.appCompat)

    implementation(Libs.AndroidX.paging3)
    testImplementation(Libs.AndroidX.paging3Common)

    with(Libs.AndroidX.Room) {
        implementation(roomKtx)
        implementation(roomPaging)
        implementation(roomRuntime)
        // Required: Room compiler (avoid RuntimeException - cannot find implementation for database)
        kapt(roomCompiler)
        androidTestImplementation(roomTesting)
    }

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

    testImplementation("junit:junit:4.13.2")
    testImplementation(Libs.mockk)
    
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
