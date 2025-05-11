import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidx.room)
}

android {
    namespace = "com.crux"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.crux"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = file("${project.rootDir}/secure_files/keystore.properties")

            if (keystorePropertiesFile.exists()) {
                val keystoreProperties = Properties().apply {
                    load(FileInputStream(keystorePropertiesFile))
                }

                storeFile = file("${project.rootDir}/${keystoreProperties.getProperty("RELEASE_STORE_FILE")}")
                storePassword = keystoreProperties.getProperty("RELEASE_STORE_PASSWORD")
                keyAlias = keystoreProperties.getProperty("RELEASE_KEY_ALIAS")
                keyPassword = keystoreProperties.getProperty("RELEASE_KEY_PASSWORD")
            } else {
                println("Warning: Keystore properties file not found. Release signing will fail.")
            }
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            signingConfig = signingConfigs.getByName("debug")
            manifestPlaceholders["appName"] = "@string/app_name_debug"
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["appName"] = "@string/app_name"
        }
    }
    applicationVariants.all {
        val variant = this
        variant.outputs
            .map { it as BaseVariantOutputImpl }
            .forEach { output ->
                output.outputFileName =
                    "crux-${variant.versionName}.apk"
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
        buildConfig = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
}

dependencies {

    // General
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.activity.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.collections.immutable)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Paging
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Pluto
    debugImplementation(libs.pluto)
    debugImplementation(libs.pluto.plugins)
    releaseImplementation(libs.pluto.no.op)
    releaseImplementation(libs.pluto.plugins.no.op)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.androidx.room.testing)
    ksp(libs.androidx.room.compiler)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Timber
    implementation(libs.timber)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}