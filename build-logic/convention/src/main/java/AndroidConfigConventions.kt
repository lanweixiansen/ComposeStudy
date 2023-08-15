@file:Suppress("unused")

import org.gradle.api.JavaVersion

object AndroidConfigConventions {
    private const val SDK_VERSION = 33
    const val MIN_SDK_VERSION = 24
    const val COMPILE_SDK_VERSION = SDK_VERSION
    const val TARGET_SDK_VERSION = SDK_VERSION

    val JAVA_VERSION = JavaVersion.VERSION_1_8

    object LittleGoose {
        private const val PACKAGE_NAME = "xxxxxxxxx"
        const val APPLICATION_ID = PACKAGE_NAME
        const val VERSION_CODE = 1
        const val VERSION_NAME = "1.0.0"
    }

}