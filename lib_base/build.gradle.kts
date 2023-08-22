plugins {
    id("goose.android.library")
}

android {
    namespace = "com.example.lib_base"
    resourcePrefix = "base_"

    defaultConfig {
        buildConfigField("String", "APPLICATION_ID", "\"${libs.versions.applicationId.get()}\"")
        buildConfigField("String", "VERSION_NAME", "\"${libs.versions.versionName.get()}\"")
        buildConfigField("String", "VERSION_CODE", "\"${libs.versions.versionCode.get()}\"")
    }
}

dependencies {
    api(project(":uiLibrary"))
    api(libs.eventBus)
    api(libs.fragmentKtx)
    ksp(libs.glideCompiler)
    api(libs.moshi)
    api(libs.liveDataKtx)
    api(libs.viewModelKtx)
    kapt(libs.theRouterApt)
    api(libs.therouter)
    compileOnly(libs.mmkv)
}