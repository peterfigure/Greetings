package com.example.greeting.domain

import arrow.core.Option
import com.example.greeting.jooq.generated.tables.pojos.Greeting

typealias GreetingId = Int

// our null safe domain wrapper for the Jooq generated Greeting
data class InternalGreeting(val id: GreetingId, val message: Option<String>) // needs to be optional as Jooq cannot correctly generate NOT NULL columns, or need to establish defaults

fun Greeting.toInternalGreeting(): InternalGreeting {
  return InternalGreeting(this.id, Option.fromNullable(this.greeting))
}