import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version Versions.Kotlin apply false
  kotlin("plugin.spring") version Versions.Kotlin apply false
}

allprojects {
  group = "com.example"
  version = "0.0.1-SNAPSHOT"

  repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/arrow-kt/arrow-kt/")
    maven {
      url = uri("https://nexus.figure.com/repository/mirror")
      credentials {
        username = System.getenv("NEXUS_USER")
        password = System.getenv("NEXUS_PASS")
      }
    }
  }
}

subprojects {

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict", "-Xinline-classes")
      jvmTarget = "11"
    }
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }
}