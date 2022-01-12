object Versions {
    const val androidGradlePlugin = "7.1.0-rc01"
    const val appCompat = "1.4.1"
    const val constraintLayout = "2.1.2"
    const val espressoCore = "3.4.0"
    const val extJunit = "1.1.3"
    const val junit = "4.13.2"
    const val kotlin = "1.6.10"
    const val ktLint = "0.43.2"
    const val ktxVersion = "1.7.0"
    const val materialComponents = "1.4.0"
}

object Libs {

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"

    const val junit = "junit:junit:${Versions.junit}"

    const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val coreKtx = "androidx.core:core-ktx:${Versions.ktxVersion}"
    }

    object Google {

        object Material {
            const val materialComponents =
                "com.google.android.material:material:${Versions.materialComponents}"
        }
    }

    object Test {
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    }
}
