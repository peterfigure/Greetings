package com.example.greeting.persistence

import arrow.core.Either
import arrow.core.Option
import arrow.fx.IO
import com.example.greeting.domain.GreetingId
import com.example.greeting.domain.InternalGreeting
import com.example.greeting.domain.toInternalGreeting
import com.example.greeting.jooq.generated.tables.daos.GreetingDao
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

interface GreetingRepository {
  fun findGreetingById(id: GreetingId): IO<Either<PersistenceError, InternalGreeting>>
}

@Repository
class GreetingRepositoryImpl(
  private val jooq: DSLContext
) : GreetingRepository {

  private val greetingDao = GreetingDao(jooq.configuration())

  override fun findGreetingById(id: GreetingId): IO<Either<PersistenceError, InternalGreeting>> {
    return dbFindOperation(id, {
      // we expect a greeting so we treat a missing greeting as an error (ie jooq returns null), so make it an Option first
      Option
        .fromNullable(greetingDao.fetchOneById(id.id))
        .map { it.toInternalGreeting() }
    })
  }
}