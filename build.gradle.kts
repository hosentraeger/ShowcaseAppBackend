
val h2_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

group = "de.fsiebecke"
version = "0.0.1"

application {
    mainClass.set("de.fsiebecke.ShowcaseAppBackend.ApplicationKt")

    val isDevelopment: Boolean = project.findProperty("isDevelopment") == "true"
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder") // Für HTML-Rendering
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-config-yaml")

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.mariadb.jdbc:mariadb-java-client:3.0.7") // Achte darauf, die neueste Version zu verwenden

    implementation("org.jetbrains.exposed:exposed-core:0.53.0") // Exposed Core
    implementation("org.jetbrains.exposed:exposed-dao:0.53.0") // Exposed DAO
    implementation("org.jetbrains.exposed:exposed-jdbc:0.53.0") // Exposed JDBC
    implementation("org.jetbrains.exposed:exposed-java-time:0.53.0")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
