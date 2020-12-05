import dev.dallagi.socialnetwork.Clock
import dev.dallagi.socialnetwork.Message
import dev.dallagi.socialnetwork.MessagesRepository
import dev.dallagi.socialnetwork.PostMessage
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Instant

class PostMessageTest {
    private val messagesRepository = mockk<MessagesRepository>(relaxed = true)
    private val fixedClock = Clock { now }

    @Test
    fun `can handle "post message" commands`() {
        assertTrue(PostMessage(messagesRepository, fixedClock).canHandle("User -> message to post on User's timeline"))
    }

    @Test
    fun `cannot handle other commands`() {
        assertFalse(PostMessage(messagesRepository, fixedClock).canHandle("any other command"))
    }

    @Test
    fun `adds message to the timeline of the recipient`() {
        PostMessage(messagesRepository, fixedClock).handle("User -> a message")

        verify {
            messagesRepository.addMessageToTimeline("User", Message("a message", now))
        }
    }

    companion object {
        private val now = Instant.parse("2020-10-10T10:10:10Z")
    }
}