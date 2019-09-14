import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":common"))

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}