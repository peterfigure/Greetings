import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("org.springframework.boot") version Versions.SpringBoot
  id("io.spring.dependency-management") version Versions.SpringDependencyManagement
  kotlin("jvm") // version comes from root build
  kotlin("plugin.spring")
}

dependencies {
  testImplementation.let {
    it(Libraries.KotlinReflect)
    it(Libraries.KotlinStdlibJdk8)
    it(Libraries.SpringBootStarterTest) {
      exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    it(Libraries.RestAssuredAll)
    it(Libraries.RestAssuredJsonPath)
    it(Libraries.RestAssuredJsonSchemaValidator)
    it(Libraries.RestAssuredKotlinExtensions)
    it(Libraries.RestAssuredSpringMockMVC)
    it(Libraries.RestAssuredSpringWebTestClient)
  }
}