import org.jetbrains.kotlin.compose.compiler.gradle.ComposeFeatureFlag

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.vaasudev.androidstructure"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.vaasudev.androidstructure"
        minSdk = 29
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

        getByName("debug") {
            isDebuggable = true
            //applicationIdSuffix = ".dubug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    flavorDimensions += "server" //listOf("api", "mode")
    productFlavors {
        create("development") {
            dimension = "server"
            buildConfigField("String", "BASE_URL", "\"https://dev.test.app/api/v5/\"")
            buildConfigField("String", "SOCKET_BASE_URL", "\"https://dev.test.app:8080/\"")
            buildConfigField("String", "SEARCH_BASE_URL", "\"https://dev.test.app:3000/\"")
//            buildConfigField("String", "IMAGE_URL", "\"https://dev.test.app/\"")
        }
        create("live") {
            dimension = "server"
            buildConfigField("String", "BASE_URL", "\"https://live.test.app/api/v5/\"")
            buildConfigField("String", "SOCKET_BASE_URL", "\"https://live.test.app:8080/\"")
//            buildConfigField("String", "IMAGE_URL", "\"https://www.test.app/\"")
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFiles.addAll(
        rootProject.layout.projectDirectory.file("stability_config.conf")
    )
    includeSourceInformation = true

    featureFlags = setOf(
        ComposeFeatureFlag.OptimizeNonSkippingGroups
    )
}

dependencies {

    //Data Store
    implementation(libs.androidx.datastore.preferences)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.retrofit2.adapter.rxjava3)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    //ViewModelScope Launch
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Compose Bom
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}