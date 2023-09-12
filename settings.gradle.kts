pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

val repoPath = File(settings.rootDir, "flutter_module/build/host/outputs/repo")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri(repoPath.absolutePath) }
        maven { url = uri("https://storage.flutter-io.cn/download.flutter.io") }
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "demo-code"
includeBuild("build-logic")
include(":app")
include(":lib_base")
include(":libHome")
include(":libSquare")
include(":lib_me")
include(":lib_news")
include(":uiLibrary")
include(":libLogin")
include(":libNet")
include(":flutter_module")
include(":sliding")
