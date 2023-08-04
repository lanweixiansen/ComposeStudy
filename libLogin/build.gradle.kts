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
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.coreKtx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.extJunit)
    androidTestImplementation(libs.espresso)
    kapt(libs.theRouterApt)
}