plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.pvsb.wavetablesythesizer"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.pvsb.wavetablesythesizer"
        minSdk = 21
        targetSdk  = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++2a"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
}

dependencies {

    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-compiler:2.45")

//
//    implementation( "androidx.activity:activity-compose:1.7.0")
//    implementation( "androidx.compose.material:material:1.4.0")
//    implementation( "androidx.constraintlayout:constraintlayout-compose:1.0.1")
//    implementation( "androidx.compose.ui:ui-tooling-preview:1.4.0")
//    implementation( "io.coil-kt:coil-compose:2.3.0")
//    implementation( "androidx.navigation:navigation-compose:2.6.0-alpha08")

    implementation ("androidx.core:core-ktx:1.8.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")


    val composeBom = platform("androidx.compose:compose-bom:2023.06.01")
    implementation(composeBom)
    androidTestImplementation(composeBom)
//
//
//
    implementation ("androidx.activity:activity-compose:1.5.1")
    implementation ("androidx.compose.ui:ui")
    implementation ("androidx.compose.ui:ui-graphics")
    implementation ("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3")

    // Choose one of the following:
    // Material Design 3
//    implementation("androidx.compose.material3:material3")
//    // or Material Design 2
//    implementation("androidx.compose.material:material")
//    // or skip Material Design and build directly on top of foundational components
//    implementation("androidx.compose.foundation:foundation")
//    // or only import the main APIs for the underlying toolkit systems,
//    // such as input and measurement/layout
//    implementation("androidx.compose.ui:ui")
//
//    // Android Studio Preview support
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    debugImplementation("androidx.compose.ui:ui-tooling")


    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4")
    debugImplementation ("androidx.compose.ui:ui-tooling")
    debugImplementation ("androidx.compose.ui:ui-test-manifest")
}




//
//plugins {
//    id("com.android.application")
//    id("org.jetbrains.kotlin.android")
//    id("kotlinx-serialization")
//    id("kotlin-kapt")
//    id("dagger.hilt.android.plugin")
//    id(SqlDelight.plugin) version SqlDelight.version
//}
//
//android {
//    namespace = "com.pvsb.locktapcompose"
//    compileSdk = 33
//
//    defaultConfig {
//        applicationId = "com.pvsb.locktapcompose"
//        minSdk = 24
//        targetSdk = 33
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.4.3"
//    }
//    packagingOptions {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//
//    implementation(project(Modules.datasource))
//    implementation(project(Modules.domain))
//    implementation(project(Modules.presentation))
//
//    implementation(AndroidX.core)
//    implementation(AndroidX.lifeCycle)
//    implementation(AndroidX.appCompat)
//    implementation(Compose.activity)
//    implementation(Compose.material)
//    implementation(Compose.constraintLayout)
//    implementation(Compose.preview)
//    implementation(Compose.coil)
//    implementation(Compose.navigation)
//    implementation(Kotlin.serialization)
//    implementation(Hilt.android)
//    implementation(Hilt.compose)
//    kapt(Hilt.compiler)
//    implementation(SqlDelight.driver)
//
//    testImplementation(Test.jUnit)
//    androidTestImplementation(Test.jUInitExt)
//    androidTestImplementation(Test.espresso)
//
//
//
//
//}