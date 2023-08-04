plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_news"
    resourcePrefix = "news_"
}

dependencies {
    implementation(project(":lib_base"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.coreKtx)
    kapt(libs.theRouterApt)
}