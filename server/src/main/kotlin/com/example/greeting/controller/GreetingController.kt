package com.example.greeting.controller

import arrow.core.Option
import arrow.core.getOrElse
import com.example.greeting.domain.GreetingId
import com.example.greeting.domain.InternalGreeting
import com.example.greeting.service.GreetingService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController // rest controllers returns domain objects (not views) = @Controller, @ResponseBody
@RequestMapping("/api/", produces = ["application/json"]) // so it will use wired jackson ObjectMapper bean to serialize to JSON (MappingJackson2HttpMessageConverter?)
class GreetingController(
  private val greetingService: GreetingService
) {
  @GetMapping("/greet/{id}")
  @ResponseBody
  // use Java Optional since that's the best we can do with Spring
  fun greet(@PathVariable(name = "id") id: GreetingId, @RequestParam(name = "name") name: Optional<String>): ResponseEntity<out Any> { // covariant because we have both String or HelloResponse
    val internalName = Option.just(name.orElseGet { "stranger" })
    return runBlockingServiceOperation {
      greetingService.getGreetingFor(id, internalName)
    }.foldResult((GreetingResponse)::fromInternalGreeting)
  }
}

// our response object that will be serialized to JSON
data class GreetingResponse(val id: GreetingId, val message: String) {
  companion object {
    fun fromInternalGreeting(g: InternalGreeting) = GreetingResponse(g.id, g.message.getOrElse { "" })
  }
}


