package com.example.greeting.controller

import arrow.core.Either
import arrow.fx.IO
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe
import com.example.greeting.persistence.PersistenceError
import com.example.greeting.service.GreetingServiceError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <R>runBlockingServiceOperation(op: () -> IO<Either<GreetingServiceError, R>>): Either<GreetingServiceError, R> {
  // spring doesn't really provide a usable non blocking API so we run our effect blocking TODO investigate further
  return unsafe { runBlocking { op() } }
}

fun <R> Either<GreetingServiceError, R>.foldResult(): ResponseEntity<*> {
  return foldResult<R, R>{ a -> a }
}

fun <R, R2> Either<GreetingServiceError, R>.foldResult(transform: (R) -> R2): ResponseEntity<*> {
  return this.fold(
    { handleGreetingServiceError(it) },
    { result -> ResponseEntity.status(HttpStatus.OK).body(transform(result)) }
  )
}

fun handleGreetingServiceError(e: GreetingServiceError): ResponseEntity<String> {
  return when (e) { // handy smart cast below
    is GreetingServiceError.InternalPersistenceError -> {
      // this gets a bit ugly since we don't have pattern matching in Kotlin :ugly-cry:
      when (e.e) {
        // TODO don't expose error text in body for production use
        is PersistenceError.ResourceNotFound<*> -> {
          // ResponseEntity.notFound() // API doesn't allow adding a body here which we need to make it visible for illustration purposes
          ResponseEntity.status(HttpStatus.NOT_FOUND).body("resource not found ${e.e.id}")
        }
        else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal error: $e")
      }
    }
  }
}
