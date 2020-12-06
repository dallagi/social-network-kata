package dev.dallagi.socialnetwork.features

import dev.dallagi.socialnetwork.Clock
import dev.dallagi.socialnetwork.Console
import dev.dallagi.socialnetwork.SocialNetworkFactory
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import java.time.Instant

class PersonalTimelineFeature {
    @Test
    fun `users can submit messages to personal timelines`() {
        val console = mockk<Console>(relaxed = true)
        val clock = mockk<Clock>(relaxed = true)
        val socialNetwork = SocialNetworkFactory.create(console, clock)
        every { clock.now() }.returnsMany(fiveMinutesAgo, twoMinutesAgo, oneMinuteAgo) andThen now

        socialNetwork.send("Alice -> I love the weather today")
        socialNetwork.send("Bob -> Damn! We lost!")
        socialNetwork.send("Bob -> Good game though.")

        socialNetwork.send("Alice")
        socialNetwork.send("Bob")

        verifySequence {
            console.printLine("I love the weather today (5 minutes ago)")
            console.printLine("Good game though. (1 minute ago)")
            console.printLine("Damn! We lost! (2 minutes ago)")
        }
    }

    companion object {
        private val now = Instant.parse("2020-10-10T10:10:10Z")
        private val oneMinuteAgo = Instant.parse("2020-10-10T10:09:10Z")
        private val twoMinutesAgo = Instant.parse("2020-10-10T10:08:10Z")
        private val fiveMinutesAgo = Instant.parse("2020-10-10T10:05:10Z")
    }
}