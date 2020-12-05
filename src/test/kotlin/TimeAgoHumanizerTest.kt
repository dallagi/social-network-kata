import dev.dallagi.socialnetwork.Clock
import dev.dallagi.socialnetwork.TimeAgoHumanizer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class TimeAgoHumanizerTest {
    private val fixedClock = Clock { now }

    @Test
    fun `returns "now" if time is the same second as now`() {
        assertEquals("now", TimeAgoHumanizer(fixedClock).humanize(now))
    }

    @Test
    fun `returns "1 second ago" if time is one second ago`() {
        assertEquals("1 second ago", TimeAgoHumanizer(fixedClock).humanize(oneSecondAgo))
    }

    @Test
    fun `returns "N seconds ago" if time is between 2 and 59 seconds ago`() {
        assertEquals("1 second ago", TimeAgoHumanizer(fixedClock).humanize(oneSecondAgo))
    }

    @Test
    fun `returns "1 minute ago" if time is between 1 and 1 and a half minutes ago`() {
        assertEquals("1 minute ago", TimeAgoHumanizer(fixedClock).humanize(oneMinuteAgo))
    }

    @Test
    fun `returns "X minutes ago" if time is roughly two or more minutes ago`() {
        assertEquals("10 minutes ago", TimeAgoHumanizer(fixedClock).humanize(tenMinutesAgo))
    }

    companion object {
        private val now = Instant.parse("2020-10-10T10:10:10Z")
        private val oneSecondAgo = Instant.parse("2020-10-10T10:10:09Z")
        private val oneMinuteAgo = Instant.parse("2020-10-10T10:09:10Z")
        private val tenMinutesAgo = Instant.parse("2020-10-10T10:00:10Z")
    }
}