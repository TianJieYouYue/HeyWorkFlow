import java.nio.file.Paths

plugins {
    kotlin("jvm") version "1.6.21"
}

allprojects{
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

task("distJars"){
    group = "build"
    dependsOn("jar")
    rootProject.subprojects {
        val project = this
        copy {
            from(Paths.get(project.projectDir.absolutePath,"build","libs"))
            include("*.jar")
            into(Paths.get(rootDir.absolutePath,"project","dist"))
        }
    }
}



