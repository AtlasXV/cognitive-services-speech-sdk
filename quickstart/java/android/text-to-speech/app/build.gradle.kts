plugins {
    id("com.android.application")
    id("kotlin-android")
}
android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.microsoft.cognitiveservices.speech.samples.quickstart"
        minSdk = 23
        targetSdk = 30
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "SUBSCRIPTION_KEY", "\"${project.subscriptionKey}\"")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Speech SDK
    implementation("com.microsoft.cognitiveservices.speech:client-sdk:1.22.0")

    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.6.21")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.3")
}
repositories {
    mavenCentral()
}
