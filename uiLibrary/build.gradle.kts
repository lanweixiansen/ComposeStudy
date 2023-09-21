plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.uilibrary"
}

dependencies {
    api(libs.lottie)
    api(libs.smartRefresh)
    api(libs.adapterHelper)
    api(libs.lifecycleRuntimeKtx)
    api(libs.blankj)
    api(libs.glide)
    api(libs.floatingX)
    api(libs.smartRefresh.falsify)
    api(libs.flexbox)
    api(libs.smartRefresh.footer)
}