plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

//    kotlin("jvm")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.myapplication02"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication02"
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
    buildFeatures {
        compose = true
    }
}

dependencies {
//    implementation(libs.androidx.room.compiler)
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.common.jvm)
//    implementation(libs.androidx.room.runtime.jvm)
    val nav_version = "2.7.2"

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("androidx.navigation:navigation-compose:${nav_version}")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${nav_version}")
    // LiveData
    implementation("androidx.compose.runtime:runtime-livedata:1.8.3")
    // StateFlow
    implementation("androidx.lifecycle:lifecycle-runtime-compose:${nav_version}")

    // Room 런타임
    implementation("androidx.room:room-runtime:${nav_version}")
    // Kotlin 확장/코루틴 지
    implementation("androidx.room:room-ktx:${nav_version}")
//    implementation("com.google.dagger:dagger-compiler:${nav_version}")
//    ksp("com.google.dagger:dagger-compiler:${nav_version}")
    // Room 컴파일러
    ksp("androidx.room:room-compiler:${nav_version}")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}