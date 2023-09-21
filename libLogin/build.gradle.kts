plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.liblogin"
    resourcePrefix = "login_"
}

dependencies {
    implementation(project(":lib_base"))
    implementation(project(":libNet"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
    ksp(libs.theRouterApt)
}