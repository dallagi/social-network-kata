import dev.dallagi.socialnetwork.MessagesRepository
import dev.dallagi.socialnetwork.PostMessage
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PostMessageTest {
    private var messagesRepository = mockk<MessagesRepository>(relaxed = true)

    @Test
    fun `can handle "post message" commands`() {
        assertTrue(PostMessage(messagesRepository).canHandle("User -> message to post on User's timeline"))
    }

    @Test
    fun `cannot handle other commands`() {
        assertFalse(PostMessage(messagesRepository).canHandle("any other command"))
    }

    @Test
    fun `adds message to the timeline of the recipient`() {
        PostMessage(messagesRepository).handle("User -> a message")

        verify {
            messagesRepository.addMessageToTimeline("User", "a message")
        }
    }
}