plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.uilibrary"
}

dependencies {
    api (libs.lottie)
    api (libs.smartRefresh)
    api (libs.adapterHelper)
    implementation (libs.appcompat)
    implementation (libs.material)
    implementation (libs.coreKtx)
    api (libs.lifecycleRuntimeKtx)
    api(libs.blankj)
    api (libs.glide)
}