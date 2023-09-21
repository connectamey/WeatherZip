// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("com.google.dagger.hilt.android") version "2.47" apply false
    id("earth.levi.dotenv-android") version "1.3.0"
}
dotenv {
    // the package name that is added to the top of your source code file: `import X.Y.Z`
    packageName = "com.amey.weatherzip"
    // the path to the source code in your Android app module. This is probably `src/main/java` but could be something else like `src/main/kotlin`
    sourcePath = "src/main/java/"
}
buildscript {

    dependencies {

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.2-2")
    }
}