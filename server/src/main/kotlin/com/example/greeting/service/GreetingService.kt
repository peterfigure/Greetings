package com.example.greeting.service

import arrow.core.Either
import arrow.core.Option
import arrow.core.Some
import arrow.core.getOrElse
import arrow.fx.IO
import com.example.greeting.domain.GreetingId
import com.example.greeting.domain.InternalGreeting
import com.example.greeting.persistence.GreetingRepository
import com.example.greeting.service.GreetingServiceError.InternalPersistenceError
import org.springframework.stereotype.Service

interface GreetingService {
  fun getGreetingFor(id: GreetingId, name: Option<String>): IO<Either<GreetingServiceError, InternalGreeting>>
}

@Service
class GreetingServiceImpl(
  private val greetingRepository: GreetingRepository
) : GreetingService {

  companion object {
    const val DefaultGreeting = "no greeting for you!"
  }

  override fun getGreetingFor(id: GreetingId, name: Option<String>): IO<Either<GreetingServiceError, InternalGreeting>> {

    return greetingRepository.findGreetingById(id).map { either ->
      either
        .mapLeft { InternalPersistenceError(it) }
        .map { g ->
          g.copy(message = Some("${g.message.getOrElse { DefaultGreeting }}${name.fold({""}, {" $it"})}"))
        }
    }
  }
}