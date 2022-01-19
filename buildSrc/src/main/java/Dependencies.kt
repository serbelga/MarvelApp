object Versions {
    const val androidGradlePlugin = "7.1.0-rc01"
    const val appCompat = "1.4.1"
    const val archCoreTesting = "2.1.0"
    const val coil = "1.4.0"
    const val constraintLayout = "2.1.2"
    const val daggerHilt = "2.40.5"
    const val espressoCore = "3.4.0"
    const val extJunit = "1.1.3"
    const val junit = "4.13.2"
    const val kotlin = "1.6.10"
    const val kotlinCoroutines = "1.6.0"
    const val ktLint = "0.43.2"
    const val ktxVersion = "1.7.0"
    const val lifecycle = "2.4.0"
    const val materialComponents = "1.5.0"
    const val mockk = "1.10.6"
    const val moshi = "1.13.0"
    const val navigation = "2.3.5"
    const val okhttp = "4.9.3"
    const val paging3 = "3.1.0"
    const val retrofit = "2.9.0"
    const val roomKtx = "2.4.1"
    const val testCore = "1.4.0"
    const val testRunner = "1.4.0"
}

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val coil = "io.coil-kt:coil:${Versions.coil}"

    const val junit = "junit:junit:${Versions.junit}"

    const val kotlinCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"
    
    const val kotlinCoroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlinCoroutines}"

    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"

    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.ktxVersion}"

        object Lifecycle {
            const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.archCoreTesting}"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        }

        object Navigation {
            const val navigationFragmentKtx =
                "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
            const val navigationSafeArgsPlugin =
                "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
            const val navigationUiKtx =
                "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        }

        const val paging3 = "androidx.paging:paging-runtime:${Versions.paging3}"
        const val paging3Common = "androidx.paging:paging-common:${Versions.paging3}"

        object Room {
            const val roomKtx = "androidx.room:room-ktx:${Versions.roomKtx}"
            const val roomPaging = "androidx.room:room-paging:${Versions.roomKtx}"
            const val roomRuntime = "androidx.room:room-runtime:${Versions.roomKtx}"
            const val roomCompiler = "androidx.room:room-compiler:${Versions.roomKtx}"
            const val roomTesting = "androidx.room:room-testing:${Versions.roomKtx}"
        }

        object Test {
            const val core = "androidx.test:core:${Versions.testCore}"
            const val coreKtx = "androidx.test:core-ktx:${Versions.testCore}"
            const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
            const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
            const val extJunitKtx = "androidx.test.ext:junit-ktx:${Versions.extJunit}"
            const val runner = "androidx.test:runner:${Versions.testRunner}"
        }
    }

    object Google {

        object Material {
            const val materialComponents =
                "com.google.android.material:material:${Versions.materialComponents}"
        }

        object Dagger {
            const val hilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
            const val hiltCompiler =
                "com.google.dagger:hilt-android-compiler:${Versions.daggerHilt}"
            const val hiltPlugin =
                "com.google.dagger:hilt-android-gradle-plugin:${Versions.daggerHilt}"
            const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.daggerHilt}"
        }
    }

    object SquareUp {

        object Moshi {
            const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
            const val moshiKotlinCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
        }

        object OkHttp3 {
            const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
        }

        object Retrofit2 {
            const val converterMoshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
            const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
            const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        }
    }

    object Test {
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    }
}
