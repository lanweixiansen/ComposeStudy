plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.libnet"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"${project.active["baseUrl"]}\"")
    }
}

dependencies {
    implementation(project(":lib_base"))
    api(libs.airbnb.mavericks)
    api(libs.retrofit2)
    implementation(libs.loggingInterceptor)
    implementation(libs.moshiKotlin)
    implementation(libs.retrofitMoshi)
    api(libs.room)
    kapt(libs.roomCompiler)
    kapt(libs.theRouterApt)
}