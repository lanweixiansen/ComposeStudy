package plugin

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementationDefaultTestDependencies(libs: VersionCatalog) {
    add("testImplementation", libs.findLibrary("junit").get())
    add("androidTestImplementation", libs.findLibrary("extJunit").get())
    add("androidTestImplementation", libs.findLibrary("espresso").get())
    add("implementation", libs.findLibrary("appcompat").get())
    add("implementation", libs.findLibrary("material").get())
    add("implementation", libs.findLibrary("coreKtx").get())
}