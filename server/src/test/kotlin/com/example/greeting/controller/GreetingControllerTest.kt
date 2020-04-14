package com.example.greeting.controller

import arrow.core.Either
import arrow.core.Option
import arrow.fx.IO
import com.example.greeting.service.GreetingResponse
import com.example.greeting.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.restassured.module.mockmvc.kotlin.extensions.*
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class GreetingControllerTest : ControllerTest() {

  @MockkBean // not @MockBean
  private lateinit var greetingService: GreetingService

  @Autowired
  private lateinit var greetingController: GreetingController

  @Test
  fun `greeting with valid name and ID`() {

    val Name = "jeff"
    val GreetingId = 1
    val Message = "howzit $Name"

    every {
      greetingService.getGreetingFor(1, Option.just(Name))
    }
    .returns(IO.just(Either.Right(GreetingResponse(GreetingId, Message))))

    val mockedMvc = MockMvcBuilders
      .standaloneSetup(greetingController)
      .build()

    Given {
      mockMvc(mockedMvc)
      param("name", Name)
    } When {
      get("/api/greet/$GreetingId")
    } Then {
      body(
        "id", Matchers.equalTo(GreetingId),
        "message", Matchers.equalTo(Message)
      )
    }
  }
}