package com.example.greeting.persistence

import arrow.core.Either
import arrow.core.Option
import arrow.core.extensions.either.monad.flatten
import arrow.fx.IO
import arrow.fx.IO.Companion.effect
import org.jooq.exception.DataAccessException
import org.jooq.exception.TooManyRowsException

sealed class PersistenceError {
  object TooManyRowsError : PersistenceError()
  data class ResourceNotFound<T>(val id: T) : PersistenceError()
  data class DataAccessError(val e: Throwable) : PersistenceError()
  data class UnknownError(val e: Throwable) : PersistenceError()
}

fun <I, T> dbFindOperation(id: I, find: () -> Option<T>): IO<Either<PersistenceError, T>> {
  return effect {
    Either
      // trap all exceptions, we don't want any propagating, all errors are encapsulated in the type system
      .catch { find() }
      // map exceptions to typed errors
      .mapLeft {
        when (it) {
          is DataAccessException -> PersistenceError.DataAccessError(it)
          is TooManyRowsException -> PersistenceError.TooManyRowsError
          is PersistenceError.ResourceNotFound<*> -> it
          else -> {
            PersistenceError.UnknownError(it)
          }
        }
      }
      // special case for find == None => ResourceNotFound
      .map { it.toEither { PersistenceError.ResourceNotFound(id) } }
      .flatten()
  }
}