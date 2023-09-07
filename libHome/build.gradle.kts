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
    implementation(project(":sliding"))
    api(libs.androidx.work.runtime.ktx)
    ksp(libs.roomCompiler)
    kapt(libs.theRouterApt)
}