import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.openjfx.javafxplugin") version "0.0.8"
}

javafx {
    version = "12.0.2"
    modules = listOf("javafx.controls")
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.3.0")

    implementation("org.koin:koin-core:2.0.1")
    implementation("com.natpryce:konfig:1.6.10.0")

    implementation(project(":ile-core"))
    implementation(project(":common"))
    implementation(project(":conduct"))
    implementation(project(":ile-dictionary"))

    implementation("io.github.microutils:kotlin-logging:1.6.24")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("org.slf4j:slf4j-api:1.7.26")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}
