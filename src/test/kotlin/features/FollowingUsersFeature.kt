package features

import dev.dallagi.socialnetwork.*
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import java.time.Instant

class FollowingUsersFeature {
    private val fixedClock = Clock { now }
    private val console = mockk<Console>(relaxed = true)
    private val postMessageClock = mockk<Clock>(relaxed = true)

    @Test
    fun `users can subscribe to other users' timelines and view aggregated list of all subscriptions`() {
        val socialNetwork = SocialNetworkFactory.create(console, writeClock = postMessageClock, readClock = fixedClock)
        every { postMessageClock.now() }.returnsMany(fiveMinutesAgo, twoSecondsAgo)

        socialNetwork.send("Alice -> I love the weather today")
        socialNetwork.send("Charlie -> I'm in New York today! Anyone wants to have a coffee?")
        socialNetwork.send("Charlie follows Alice")

        socialNetwork.send("Charlie wall")

        verifySequence {
            console.printLine("Charlie - I'm in New York today! Anyone wants to have a coffee? (2 seconds ago)")
            console.printLine("Alice - I love the weather today (5 minutes ago)")
        }
    }

    companion object {
        private val now = Instant.parse("2020-10-10T10:10:10Z")
        private val twoSecondsAgo = Instant.parse("2020-10-10T10:10:08")
        private val fiveMinutesAgo = Instant.parse("2020-10-10T10:05:10Z")
    }
}