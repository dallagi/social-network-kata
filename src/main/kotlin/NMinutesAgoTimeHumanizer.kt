package dev.dallagi.socialnetwork

import java.time.Instant

class NMinutesAgoTimeHumanizer(private val clock: Clock) : TimeHumanizer {
    override fun humanize(time: Instant): String {
        return when (val seconds = secondsFrom(time)) {
            in NOW -> "now"
            in ONE_MINUTE -> "1 minute ago"
            else -> "${secondsToMinutes(seconds)} minutes ago"
        }
    }

    private fun secondsFrom(time: Instant) = millisToSeconds(clock.now().toEpochMilli() - time.toEpochMilli())

    private fun millisToSeconds(millis: Long) = millis / 1000

    private fun secondsToMinutes(seconds: Long) = seconds / 60

    companion object {
        private val NOW = 0..30
        private val ONE_MINUTE = 31..90
    }
}