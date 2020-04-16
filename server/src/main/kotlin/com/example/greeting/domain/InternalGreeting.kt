package com.example.greeting.domain

import arrow.core.Option
import com.example.greeting.jooq.generated.tables.pojos.Greeting

inline class Id<T>(val id: Int) {
  fun naked(): Int = id
}
typealias GreetingId = Id<InternalGreeting>

// our null safe domain wrapper for the Jooq generated Greeting
data class InternalGreeting(val id: GreetingId, val message: Option<String>) // needs to be optional as Jooq cannot correctly generate NOT NULL columns, or need to establish defaults

fun Greeting.toInternalGreeting(): InternalGreeting {
  return InternalGreeting(GreetingId(this.id), Option.fromNullable(this.greeting))
}