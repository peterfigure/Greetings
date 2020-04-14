package com.example.greeting.controller

import io.mockk.junit5.MockKExtension
import io.restassured.RestAssured
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@ExtendWith(MockKExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class ControllerTest {
  @LocalServerPort
  private val port = 0

  @BeforeEach
  fun setUp() {
    RestAssured.port = port
  }
}