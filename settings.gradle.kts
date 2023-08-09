import java.net.URI

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            // win 前需要加 / , mac前不用
            url = URI("file:/flutter_module/build/host/outputs/repo")
        }
        maven {
            url = URI("https://storage.flutter-io.cn/download.flutter.io")
        }
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
