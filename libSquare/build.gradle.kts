plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_square"
    resourcePrefix = "square_"
}

dependencies {
    implementation(project(":lib_base"))
    ksp(libs.theRouterApt)
}