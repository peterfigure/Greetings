object Versions {
  // plugins
  const val Kotlin = "1.3.72"
  const val SpringBoot = "2.2.6.RELEASE"
  const val SpringDependencyManagement = "1.0.9.RELEASE"
  const val Flyway = "6.3.3"
  const val JooqPlugin = "4.1"

  // dependencies
  const val Arrow = "0.10.5"
  const val Jackson = "2.10.3"
  const val Protobuf = "3.11.4"
  const val Postgres = "42.2.5"
  const val RestAssured = "4.2.0" // 4.3.0 does not work: https://github.com/rest-assured/rest-assured/issues/1305
  const val SpringMock = "2.0.1"
}

object Libraries {
  const val KotlinReflect = "org.jetbrains.kotlin:kotlin-reflect"
  const val KotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

  const val SpringBootStarterJooq = "org.springframework.boot:spring-boot-starter-jooq:${Versions.SpringBoot}"
  const val SpringBootStarterJetty = "org.springframework.boot:spring-boot-starter-jetty:${Versions.SpringBoot}"
  const val SpringBootStarterWeb = "org.springframework.boot:spring-boot-starter-web:${Versions.SpringBoot}"
  const val SpringBootStarterTomcat = "org.springframework.boot:spring-boot-starter-tomcat:${Versions.SpringBoot}"
  const val SpringBootStarterTest = "org.springframework.boot:spring-boot-starter-test:${Versions.SpringBoot}"
  const val SpringBootDevTools = "org.springframework.boot:spring-boot-devtools:${Versions.SpringBoot}"

  const val JacksonModuleKotlin = "com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.Jackson}"
  const val JacksonModuleJava8 = "com.fasterxml.jackson.module:jackson-modules-java8:${Versions.Jackson}"
  const val FlywayCore = "org.flywaydb:flyway-core:${Versions.Flyway}"

  const val Postgres = "org.postgresql:postgresql:${Versions.Postgres}"

  const val ArrowFx = "io.arrow-kt:arrow-fx:${Versions.Arrow}"
  const val ArrowSyntax = "io.arrow-kt:arrow-syntax:${Versions.Arrow}"
  const val ArrowMeta = "io.arrow-kt:arrow-meta:${Versions.Arrow}"

  const val RestAssured = "io.rest-assured:rest-assured:${Versions.RestAssured}"
  const val RestAssuredAll = "io.rest-assured:rest-assured-all:${Versions.RestAssured}"
  const val RestAssuredXmlPath = "io.rest-assured:xml-path:${Versions.RestAssured}"
  const val RestAssuredJsonPath = "io.rest-assured:json-path:${Versions.RestAssured}"
  const val RestAssuredJsonSchemaValidator = "io.rest-assured:json-schema-validator:${Versions.RestAssured}"
  const val RestAssuredSpringMockMVC = "io.rest-assured:spring-mock-mvc:${Versions.RestAssured}"
  const val RestAssuredSpringMockMVCKotlin = "io.rest-assured:spring-mock-mvc-kotlin-extensions:${Versions.RestAssured}"
  const val RestAssuredSpringWebTestClient = "io.rest-assured:spring-web-test-client:${Versions.RestAssured}"
  const val RestAssuredKotlinExtensions = "io.rest-assured:kotlin-extensions:${Versions.RestAssured}"

  const val SpringMockk = "com.ninja-squad:springmockk:${Versions.SpringMock}"
}

const val implementation = "implementation"
const val testImplementation = "testImplementation"