package com.example.greeting.service

import com.example.greeting.persistence.PersistenceError

sealed class GreetingServiceError {
  data class InternalPersistenceError(val e: PersistenceError) : GreetingServiceError()
}
