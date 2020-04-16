package com.example.greeting.controller

import arrow.core.Either
import arrow.core.Option
import arrow.core.Some
import arrow.fx.IO
import com.example.greeting.domain.GreetingId
import com.example.greeting.domain.InternalGreeting
import com.example.greeting.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.restassured.http.ContentType
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
    val GreetingId = GreetingId(1)
    val Message = "howzit $Name"

    every {
      greetingService.getGreetingFor(GreetingId, Option.just(Name))
    }
    .returns(IO.just(Either.Right(InternalGreeting(GreetingId, Some(Message)))))

    val mockedMvc = MockMvcBuilders
      .standaloneSetup(greetingController)
      .build()

    fun greetingQuery(id: GreetingId) = "/api/greet/${id.naked()}" // have to force unboxing

    Given {
      mockMvc(mockedMvc)
      param("name", Name)
    } When {
      get(greetingQuery(GreetingId))
    } Then {
      contentType(ContentType.JSON)
      body(
        "id", Matchers.equalTo(GreetingId.naked()), // have to force unboxing, cannot extend java classes
        "message", Matchers.equalTo(Message)
      )
    }
  }
}