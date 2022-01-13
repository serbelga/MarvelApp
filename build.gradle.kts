buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Libs.androidGradlePlugin)
        classpath(Libs.kotlinPlugin)
        classpath(Libs.Google.Dagger.hiltPlugin)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
