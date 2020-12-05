package dev.dallagi.socialnetwork

import java.time.Instant

class TimeAgoHumanizer(private val clock: Clock) : TimeHumanizer {
    override fun humanize(time: Instant): String {
        return when (val seconds = secondsFrom(time)) {
            0L -> "now"
            1L -> "1 second ago"
            in SECONDS -> "$seconds seconds ago"
            in ONE_MINUTE -> "1 minute ago"
            else -> "${secondsToMinutes(seconds)} minutes ago"
        }
    }

    private fun secondsFrom(time: Instant) = millisToSeconds(clock.now().toEpochMilli() - time.toEpochMilli())

    private fun millisToSeconds(millis: Long) = millis / 1000

    private fun secondsToMinutes(seconds: Long) = seconds / 60

    companion object {
        private val ONE_MINUTE = 60..90
        private val SECONDS = 2..59
    }
}