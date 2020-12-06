package dev.dallagi.socialnetwork

import java.time.Instant

data class Message(val recipient: String, val body: String, val time: Instant)