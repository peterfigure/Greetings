package com.example.greeting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication // == @Configuration @EnableAutoConfiguration @ComponentScan
class GreetingApplication

fun main(args: Array<String>) {
  runApplication<GreetingApplication>(*args)
}
