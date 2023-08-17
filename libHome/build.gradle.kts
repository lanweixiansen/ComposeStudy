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
    ksp(libs.roomCompiler)
    ksp(libs.theRouterApt)
}