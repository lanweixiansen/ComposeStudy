plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_home"
    resourcePrefix = "home_"
}

dependencies {
    implementation(project(":lib_base"))
    implementation(project(":libNet"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.coreKtx)
    kapt(libs.roomCompiler)
    kapt(libs.theRouterApt)
}