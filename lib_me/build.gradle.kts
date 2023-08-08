plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_me"
    resourcePrefix = "me_"
}

dependencies {
    implementation(project(":lib_base"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.coreKtx)
    kapt(libs.theRouterApt)
    debugCompileOnly(libs.flutterDebug)
    releaseCompileOnly(libs.flutterRelease)
}