plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
//    id("kotlin-kapt")
    alias(libs.plugins.compose.compiler)
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.deliveryapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.deliveryapp"
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    viewBinding {
        enable = true
     }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.jwtdecode)

    // Retrofit
//    implementation(libs.retrofit)
//    implementation(libs.converter.gson)

    // OkHttp
    implementation(libs.logging.interceptor)

    implementation(libs.osmdroid.android)

    //roomDB
//    implementation(libs.androidx.room.runtime)
//    kapt(libs.androidx.room.compiler.v260)

    implementation(libs.androidx.room.ktx)

    val composeBom = platform("androidx.compose:compose-bom:2024.10.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)
    // Compose + M3
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.runtime.livedata)

    // navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.converter.gson)

    //roomdb
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    //location
    implementation(libs.play.services.location)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}