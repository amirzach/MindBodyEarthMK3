import com.android.build.api.dsl.Packaging

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

            // Updated packaging block
            packaging {
                resources {
                    excludes += setOf(
                        "META-INF/INDEX.LIST",
                        "META-INF/DEPENDENCIES",
                        "META-INF/NOTICE",
                        "META-INF/LICENSE",
                        "META-INF/LICENSE.txt",
                        "META-INF/NOTICE.txt"
                    )
                }
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
            testImplementation(libs.junit)
            androidTestImplementation(libs.ext.junit)
            androidTestImplementation(libs.espresso.core)
            annotationProcessor(libs.room.compiler)
            implementation("com.google.cloud:google-cloud-dialogflow:2.0.0")
            implementation("com.google.cloud:google-cloud-storage:1.113.1")
            implementation("com.google.firebase:firebase-auth:21.0.1")
            implementation("com.google.firebase:firebase-database:20.0.3")
            implementation("com.google.android.material:material:1.4.0")
        }
    }
}