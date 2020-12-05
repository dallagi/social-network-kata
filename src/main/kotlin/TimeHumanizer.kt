package dev.dallagi.socialnetwork

import java.time.Instant

fun interface TimeHumanizer {
    fun humanize(time: Instant): String
}
