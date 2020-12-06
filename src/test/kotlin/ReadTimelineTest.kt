package dev.dallagi.socialnetwork

import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant

class ReadTimelineTest {
    private val console = mockk<Console>(relaxed = true)
    private val messagesRepository = mockk<MessagesRepository>()
    private val timeHumanizer = TimeHumanizer {
        when (it) {
            oneMinuteAgo -> "1 minute ago"
            tenMinutesAgo -> "10 minutes ago"
            else -> ""
        }
    }

    @Test
    fun `can handle "read timeline" commands`() {
        val readTimeline = ReadTimeline(timeHumanizer, messagesRepository, console)

        assertTrue(readTimeline.canHandle("User"))
    }

    @Test
    fun `does not handle any other command`() {
        val readTimeline = ReadTimeline(timeHumanizer, messagesRepository, console)

        assertFalse(readTimeline.canHandle("User -> a message"))
    }

    @Test
    fun `prints all messages from a timeline on the console`() {
        val messages = listOf(
            Message("User", "a message", tenMinutesAgo),
            Message("User", "another message", oneMinuteAgo)
        )
        every { messagesRepository.allMessagesOnTimeline("User") } returns messages
        val readTimeline = ReadTimeline(timeHumanizer, messagesRepository, console)

        readTimeline.handle("User")

        verifySequence {
            console.printLine("another message (1 minute ago)")
            console.printLine("a message (10 minutes ago)")
        }
    }

    companion object {
        private val oneMinuteAgo = Instant.parse("2020-10-10T10:09:00Z")
        private val tenMinutesAgo = Instant.parse("2020-10-10T10:00:00Z")
    }
}