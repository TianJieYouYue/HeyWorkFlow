import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
group = "online.heyworld.workflow"


dependencies {
    implementation(fileTree("libs"))
    implementation(project(":framework:common"))
    implementation("ch.qos.logback:logback-classic:1.4.0")
    testImplementation(kotlin("test"))
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}