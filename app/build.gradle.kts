plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.21"
    kotlin("kapt")
}

android {
    namespace = "com.app.user"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.user"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":features:user"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

//    hilt injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // For ViewModel injection
    implementation(libs.androidx.hilt.navigation.compose)

    //     Compose BOM (keeps all Compose libraries in sync)
    implementation (platform(libs.androidx.compose.bom))

    // Material 3
    implementation (libs.material3)

    // Core Compose UI libs
    implementation (libs.androidx.ui)
    implementation (libs.androidx.ui.tooling.preview)
    implementation (libs.androidx.foundation)
    implementation (libs.androidx.runtime)
}