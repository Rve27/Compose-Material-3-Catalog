/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.Properties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.plugin.compose)
}

android {
    namespace = "androidx.compose.material3.catalog"
    compileSdk = 36
    buildToolsVersion = "36.1.0"
    ndkVersion = "29.0.14206865"

    signingConfigs {
        create("release") {
            val propertiesFile = rootProject.file("signing.properties")
            val properties = if (propertiesFile.exists()) {
                Properties().apply {
                    load(propertiesFile.inputStream())
                }
            } else {
                null
            }

            val getString: (String, String, String) -> String? = { propertyName, environmentName, prompt ->
                properties?.getProperty(propertyName) ?: System.getenv(environmentName)
                    ?: System.console()?.readLine("\n$prompt: ")
            }

            storeFile = getString("storeFile", "STORE_FILE", "Store file")?.let { rootProject.file(it) }
            storePassword = getString("storePassword", "STORE_PASSWORD", "Store password")
            keyAlias = getString("keyAlias", "KEY_ALIAS", "Key alias")
            keyPassword = getString("keyPassword", "KEY_PASSWORD", "Key password")
        }
    }

    defaultConfig {
        applicationId = "androidx.compose.material3.catalog"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    packaging {
        jniLibs {
            useLegacyPackaging = true
        }

        dex {
            useLegacyPackaging = true
        }

        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.adaptive)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.adaptive.navigation)
    implementation(libs.androidx.material3.adaptive.navigation.suite)
    implementation(libs.androidx.material3.window.size)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended.android)
    implementation(libs.androidx.material)

    implementation(libs.androidx.animation)
    implementation(libs.androidx.animation.core)
    implementation(libs.androidx.animation.graphics)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.material.ripple)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.runtime.rxjava2)
    implementation(libs.androidx.runtime.rxjava3)
    implementation(libs.androidx.runtime.saveable)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.geometry)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.test)
    implementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.ui.text)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.data)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.ui.unit)
    implementation(libs.androidx.ui.util)
    implementation(libs.androidx.ui.viewbinding)
}
