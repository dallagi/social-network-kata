import dev.dallagi.socialnetwork.InMemoryMessagesRepository
import dev.dallagi.socialnetwork.Message
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class InMemoryMessagesRepositoryTest {
    private val anInstant = Instant.parse("2020-10-10T10:10:10Z")

    @Test
    fun `can post a message and read it`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessageToTimeline("Recipient", Message("a message", anInstant))

        assertIterableEquals(
            listOf(Message("a message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }

    @Test
    fun `can post multiple messages and read them`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessageToTimeline("Recipient", Message("a message", anInstant))
        messagesRepository.addMessageToTimeline("Recipient", Message("another message", anInstant))

        assertIterableEquals(
            listOf(Message("a message", anInstant), Message("another message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }

    @Test
    fun `reads only messages posted on the specified timeline`() {
        val messagesRepository = InMemoryMessagesRepository()

        messagesRepository.addMessageToTimeline("Recipient", Message("a message", anInstant))
        messagesRepository.addMessageToTimeline("SomeoneElse", Message("another message", anInstant))

        assertIterableEquals(
            listOf(Message("a message", anInstant)),
            messagesRepository.allMessagesOnTimeline("Recipient")
        )
    }
}