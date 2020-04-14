package com.example.greeting.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.getOrElse
import arrow.fx.IO
import com.example.greeting.domain.GreetingId
import com.example.greeting.domain.InternalGreeting
import com.example.greeting.persistence.GreetingRepository
import com.example.greeting.service.GreetingServiceError.InternalPersistenceError
import org.springframework.stereotype.Service

// our response object that will be serialized to JSON
data class GreetingResponse(val id: GreetingId, val message: String) {
  companion object {
    const val DefaultGreeting = "no greeting for you!"

    fun fromInternalGreeting(g: InternalGreeting, name: Option<String>, defaultMessage: String = DefaultGreeting): GreetingResponse {
      return GreetingResponse(g.id, "${g.message.getOrElse { defaultMessage }}${name.fold({""}, {" $it"})}")
    }
  }
}

interface GreetingService {
  fun getGreetingFor(id: GreetingId, name: Option<String>): IO<Either<GreetingServiceError, GreetingResponse>>
}

@Service
class GreetingServiceImpl(
  private val greetingRepository: GreetingRepository
) : GreetingService {

  override fun getGreetingFor(id: GreetingId, name: Option<String>): IO<Either<GreetingServiceError, GreetingResponse>> {

    return greetingRepository.findGreetingById(id).map {
      it
        .mapLeft { InternalPersistenceError(it) }
        .map { GreetingResponse.fromInternalGreeting(it, name) }
    }
  }
}