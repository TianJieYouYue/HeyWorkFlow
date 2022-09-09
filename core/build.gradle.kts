import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
group = "online.heyworld.workflow"


dependencies {
    implementation(fileTree("libs"))
    api("com.google.code.gson:gson:2.9.0")
    api("com.google.guava:guava:31.1-jre")
    api("org.slf4j:slf4j-api:2.0.0")

    testImplementation(kotlin("test"))
}


tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}