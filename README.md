Simple sample app using 
* Kotlin https://kotlinlang.org/ 
* Arrow https://arrow-kt.io/
* Spring Boot https://spring.io/projects/spring-boot
* Jooq https://www.jooq.org/
* Rest Assured https://github.com/rest-assured/rest-assured/
* SpringMockk https://github.com/Ninja-Squad/springmockk 

It illustrates a more functional approach to the common Controller/Service/Persistence web application. 
 
# Build

There are two modules in this Gradle multi module build: `buildSrc`, `server`. 

## buildSrc

https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources

Allows us to easily represent constants (mostly for plugin & dependency version numbers and dependencies) to
 be represented in Kotlin (ala SBT `project/Dependencies.scala`). This allows use of IntelliJ to navigate the build
, search for dependency usage etc.  

# Running the App
```
 docker-compose up -d
 ./gradlew generateGreetingJooqSchemaSource
 ./gradlew bootRun
``` 
