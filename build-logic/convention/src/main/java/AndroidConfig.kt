import org.gradle.api.Project

val Project.debug: Map<String, String>
    get() = mapOf(
        "baseUrl" to "https://www.wanandroid.com"
    )

val Project.release: Map<String, String>
    get() = mapOf(
        "baseUrl" to "https://www.wanandroid.com"
    )

val Project.active: Map<String, String>
    get() = debug