plugins {
    alias(libs.plugins.buildLogicLint)

    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    alias(libs.plugins.kotlinx.rpc)
}

group = "me.tbsten.prac.websocketdebugtool"
version = "1.0.0"
application {
    mainClass.set("me.tbsten.prac.websocketdebugtool.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.kotlinx.rpc.server)
    implementation(libs.kotlinx.rpc.serialization)
    implementation(libs.kotlinx.rpc.ktor.server)
    testImplementation(libs.ktor.server.tests)
    testImplementation(libs.kotlin.test.junit)
}
