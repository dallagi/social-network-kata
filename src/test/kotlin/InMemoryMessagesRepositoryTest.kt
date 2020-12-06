package dev.dallagi.socialnetwork

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class InMemoryMessagesRepositoryTest {
    private val anInstant = Instant.parse("2020-10-10T10:10:10Z")

    @Test
    fun `can post a message and read it`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessage(Message("Recipient", "a message", anInstant))

        assertIterableEquals(
            listOf(Message("Recipient", "a message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }

    @Test
    fun `can post multiple messages and read them`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessage(Message("Recipient", "a message", anInstant))
        messagesRepository.addMessage(Message("Recipient", "another message", anInstant))

        assertIterableEquals(
            listOf(Message("Recipient", "a message", anInstant), Message("Recipient", "another message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }

    @Test
    fun `reads only messages posted on the specified timeline`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessage(Message("Recipient", "a message", anInstant))
        messagesRepository.addMessage(Message("SomeoneElse", "another message", anInstant))

        assertIterableEquals(
            listOf(Message("Recipient", "a message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }
}