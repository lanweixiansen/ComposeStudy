plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_me"
    resourcePrefix = "me_"
}

dependencies {
    implementation(project(":lib_base"))
    ksp(libs.theRouterApt)
    debugCompileOnly(libs.flutterDebug)
    releaseCompileOnly(libs.flutterRelease)
}