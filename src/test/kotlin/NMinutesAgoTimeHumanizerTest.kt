import dev.dallagi.socialnetwork.Clock
import dev.dallagi.socialnetwork.NMinutesAgoTimeHumanizer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class NMinutesAgoTimeHumanizerTest {
    private val fixedClock = Clock { now }

    @Test
    fun `returns "now" if time is within thirty seconds of difference than now`() {
        assertEquals("now", NMinutesAgoTimeHumanizer(fixedClock).humanize(now))
    }

    @Test
    fun `returns "1 minute ago" if time is roughly one minute ago`() {
        assertEquals("1 minute ago", NMinutesAgoTimeHumanizer(fixedClock).humanize(oneMinuteAgo))
    }

    @Test
    fun `returns "X minutes ago" if time is roughly two or more minutes ago`() {
        assertEquals("10 minutes ago", NMinutesAgoTimeHumanizer(fixedClock).humanize(tenMinutesAgo))
    }

    companion object {
        private val now = Instant.parse("2020-10-10T10:10:10Z")
        private val oneMinuteAgo = Instant.parse("2020-10-10T10:09:10Z")
        private val tenMinutesAgo = Instant.parse("2020-10-10T10:00:10Z")
    }
}