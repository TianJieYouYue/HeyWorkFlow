import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
}

allprojects{
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

