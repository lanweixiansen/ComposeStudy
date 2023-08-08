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
    api (libs.lifecycleRuntimeKtx)
    api(libs.blankj)
    api (libs.glide)
}