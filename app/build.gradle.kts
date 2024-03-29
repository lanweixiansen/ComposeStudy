plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("therouter")
}

android {
    namespace = "com.example.demoapplication"

    signingConfigs {
        create("release") {
            storeFile = file("spk.jks")
            storePassword = "123456"
            keyAlias = "spk"
            keyPassword = "123456"
        }
    }

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
//        ndk {
//            abiFilters.add("armeabi-v7a")
//            abiFilters.add("arm64-v8a")
//        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }

        getByName("release") {
            isDebuggable = true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java) {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            //Room官方建议：在`android`块中添加以下代码块，以从软件包中排除原子函数模块并防止出现警告
            excludes.add("/META-INF/atomicfu.kotlin_module")
        }
    }

    android.applicationVariants.all {
        val buildType = this.buildType.name
        outputs.all {
            if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                this.outputFileName =
                    "demo-${defaultConfig.versionName}-${defaultConfig.versionCode}-$buildType.apk"
            }
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(project(":lib_base"))
    implementation(project(":libHome"))
    implementation(project(":libSquare"))
    implementation(project(":lib_news"))
    implementation(project(":lib_me"))
    implementation(project(":uiLibrary"))
    implementation(project(":libLogin"))
    implementation(project(":libNet"))
    implementation(libs.androidx.splash.screen)
    implementation(libs.coreKtx)
    debugImplementation(libs.leakcanary)
    ksp(libs.theRouterApt)
    implementation(libs.mmkv)
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
    implementation(libs.appcompat)
    implementation(libs.material)
    debugImplementation(libs.flutterDebug)
    releaseImplementation(libs.flutterRelease)
}
