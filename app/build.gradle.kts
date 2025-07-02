plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.alana.kanban"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.alana.kanban"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

configurations.all {
    exclude(group = "com.intellij", module = "annotations")
    exclude(group = "org.jetbrains", module = "annotations")
}

configurations.all {
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jetbrains" && requested.name == "annotations") {
                useVersion("23.0.0")
                because("Evitar conflito de versões duplicadas das annotations")
            }
            if (requested.group == "com.intellij" && requested.name == "annotations") {
                useVersion("23.0.0")
                because("Evitar conflito de versões duplicadas das annotations")
            }
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}