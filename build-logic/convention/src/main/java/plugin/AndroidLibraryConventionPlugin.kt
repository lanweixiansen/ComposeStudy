package plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.PluginManager
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

@Suppress("unused")
class AndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        with(project) {
            pluginManager.applyPlugin()
            applyAndroid<LibraryExtension> {
                configureKotlin(this)
                configureAndroid()
            }
            applyDependencies()
        }
    }

    private fun PluginManager.applyPlugin() {
        apply("com.android.library")
        apply("org.jetbrains.kotlin.android")
        apply("kotlin-parcelize")
        apply("kotlin-kapt")
    }

    private fun LibraryExtension.configureAndroid() {
        configureAndroidCommon()
    }

    private fun Project.applyDependencies() {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        dependencies {
            implementationDefaultTestDependencies(libs)
        }
    }
}