import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version Versions.SpringBoot
  id("io.spring.dependency-management") version Versions.SpringDependencyManagement
  kotlin("jvm") // version comes from root build
  kotlin("plugin.spring") // version comes from root build
  kotlin("kapt") // not sure where this version comes from?? transitive from jvm?? 
  id("nu.studer.jooq") version Versions.JooqPlugin // not compatible with kotlin gradle syntax: https://github.com/etiennestuder/gradle-jooq-plugin/issues/62#issuecomment-513394067
  // id("com.rohanprabhu.kotlin-dsl-jooq") version "0.4.5"
  id("org.flywaydb.flyway") version Versions.Flyway
}

// TODO do we need this?
val developmentOnly by configurations.creating
configurations {
  runtimeClasspath {
    extendsFrom(developmentOnly)
  }
}

dependencies {
  implementation.let {
    // Kotlin
    it(Libraries.KotlinReflect)
    it(Libraries.KotlinStdlibJdk8)

    // Spring
    it(Libraries.SpringBootStarterWeb) {
      exclude(Libraries.SpringBootStarterTomcat)
    }
    it(Libraries.SpringBootStarterJetty)
    it(Libraries.SpringBootStarterJooq)
 
    it(Libraries.JacksonModuleKotlin)
    // open build issue: https://github.com/arrow-kt/arrow/issues/2032
    it(Libraries.ArrowFx)
    it(Libraries.ArrowSyntax)
  }
  developmentOnly(Libraries.SpringBootDevTools)
  runtimeOnly(Libraries.JacksonModuleJava8)
  runtimeOnly(Libraries.FlywayCore)
  runtimeOnly(Libraries.Postgres) // for running flyway when the app starts up
  jooqRuntime(Libraries.Postgres) // for the generator
  kapt(Libraries.ArrowMeta)

  testImplementation.let {
    it(Libraries.SpringBootStarterTest) {
      exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
      exclude(group = "org.mockito", module = "mockito-core")
    }
    it(Libraries.RestAssured)
    it(Libraries.RestAssuredXmlPath)
    it(Libraries.RestAssuredJsonPath)
    it(Libraries.RestAssuredJsonSchemaValidator)
    it(Libraries.RestAssuredSpringMockMVC)
    it(Libraries.RestAssuredSpringMockMVCKotlin)
    it(Libraries.RestAssuredKotlinExtensions)
    it(Libraries.RestAssuredSpringWebTestClient)
    it(Libraries.SpringMockk)
  }
}

apply(from = "useJooq.gradle")
