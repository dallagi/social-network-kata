package dev.dallagi.socialnetwork

import java.time.Instant

fun interface Clock {
    fun now(): Instant
}