plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.bottomanimationmydemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bottomanimationmydemo"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // text size
    implementation("com.intuit.ssp:ssp-android:1.0.6")
    implementation("com.intuit.sdp:sdp-android:1.0.6")

    //Jetpack navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0-alpha01")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0-alpha01")
    implementation("androidx.navigation:navigation-runtime-ktx:2.4.0-alpha01")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Constraints layout with motion layout dependency
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")

    implementation("com.github.bumptech.glide:glide:4.13.2")
//region Hilt for di
    implementation("com.google.dagger:hilt-android:2.42")
    kapt("com.google.dagger:hilt-android-compiler:2.42")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.3.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.3.1")
    implementation("com.localebro:okhttpprofiler:1.0.8")
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    implementation("com.google.code.gson:gson:2.8.9")

    //region Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0") //viewModelScope
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")

    //Jetpack navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.4.0-alpha01")
    implementation("androidx.navigation:navigation-ui-ktx:2.4.0-alpha01")
    implementation("androidx.navigation:navigation-runtime-ktx:2.4.0-alpha01")

    //region Timber for logging without TAG
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("androidx.fragment:fragment-ktx:1.4.0-alpha01")

    implementation("com.airbnb.android:lottie:4.1.0")
    implementation("com.github.marcinmoskala:ArcSeekBar:0.31")

    implementation("com.github.smarteist:autoimageslider:1.4.0")
    implementation("com.github.whilu:AndroidTagView:1.1.7")
    // Dependency on a local library module
    implementation(project(":vimeoplayer2"))
}