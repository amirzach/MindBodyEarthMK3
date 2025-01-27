plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.mindbodyearthmk3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mindbodyearthmk3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    implementation(libs.room.runtime)
    implementation(libs.generativeai)
//    implementation(libs.generativeai)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    annotationProcessor(libs.room.compiler)
    implementation (libs.room.rxjava2)
    implementation (libs.lifecycle.viewmodel)
    implementation (libs.androidx.lifecycle.livedata)
    implementation("com.google.ai.client.generativeai:generativeai:0.2.1")
//    implementation ("com.squareup.okhttp3:okhttp:4.10.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

}